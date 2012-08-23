package org.levigo.jadice.server.camel.jobdef;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.StreamCache;
import org.apache.camel.converter.stream.CachedOutputStream;
import org.apache.camel.util.IOHelper;

import com.levigo.jadice.server.Job;
import com.levigo.jadice.server.Node;
import com.levigo.jadice.server.client.JobFactory;
import com.levigo.jadice.server.nodes.StreamInputNode;
import com.levigo.jadice.server.nodes.StreamOutputNode;

public abstract class ConvertMessageBodyJobDefinition implements JobDefinition
{

   public ConvertMessageBodyJobDefinition()
   {
      super();
   }

   protected abstract void configureNodes(Node inputNode, Node outputNode) throws Exception;

   @Override
   public void execute(JobFactory jobFactory, Exchange exchange) throws Exception
   {

      StreamInputNode sin = new StreamInputNode();
      StreamOutputNode son = new StreamOutputNode();

      configureNodes(sin, son);

      Job job = jobFactory.createJob();
      job.attach(sin);
      job.submit();

      StreamCache body = exchange.getIn().getMandatoryBody(StreamCache.class);

      OutputStream out = sin.getOutputStream();
      body.writeTo(out);
      body.reset();
      out.flush();
      out.close();
      sin.complete();

      // consume the result
      InputStream result = son.getStreamBundle().iterator().next().getInputStream();

      CachedOutputStream cachedResult = new CachedOutputStream(exchange);
      IOHelper.copyAndCloseInput(result, cachedResult);

      son.getStreamBundle().complete();

      try
      {
         job.close();
      }
      catch (Exception e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      StreamCache resultStreamCache = cachedResult.getStreamCache();

      if (exchange.getPattern().isOutCapable())
      {

         exchange.getOut().setBody(resultStreamCache);

         // copy headers
         exchange.getOut().getHeaders().putAll(exchange.getIn().getHeaders());
      }
      else
      {
         exchange.getIn().setBody(resultStreamCache);
      }

   }

}
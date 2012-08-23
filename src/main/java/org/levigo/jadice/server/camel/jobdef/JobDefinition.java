package org.levigo.jadice.server.camel.jobdef;

import org.apache.camel.Exchange;

import com.levigo.jadice.server.client.JobFactory;

public interface JobDefinition
{
   void execute(JobFactory jobFactory, Exchange exchange) throws Exception;
}

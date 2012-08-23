package org.levigo.jadice.server.camel;

import com.levigo.jadice.server.client.JobFactory;

public class JadiceServerConfiguration
{
   private JobFactory jobFactory;

   public JobFactory getJobFactory()
   {
      return jobFactory;
   }

   public JadiceServerConfiguration()
   {
   }

   public JadiceServerConfiguration(JobFactory jobFactory)
   {
      super();
      this.jobFactory = jobFactory;
   }

   public void setJobFactory(JobFactory jobFactory)
   {
      this.jobFactory = jobFactory;
   }

}

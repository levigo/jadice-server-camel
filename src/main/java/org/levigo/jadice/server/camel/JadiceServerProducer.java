/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.levigo.jadice.server.camel;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.levigo.jadice.server.camel.jobdef.JobDefinition;

/**
 * The JadiceServer producer.
 */
public class JadiceServerProducer extends DefaultProducer
{
//   private static final transient Logger LOG = LoggerFactory.getLogger(JadiceServerProducer.class);

   public JadiceServerProducer(JadiceServerEndpoint endpoint)
   {
      super(endpoint);
   }

   public void process(Exchange exchange) throws Exception
   {

      JobDefinition jobDefinition = getEndpoint().getJobDefinition();
      jobDefinition.execute(getEndpoint().getConfiguration().getJobFactory(), exchange);

   }

   @Override
   public JadiceServerEndpoint getEndpoint()
   {
      return (JadiceServerEndpoint) super.getEndpoint();
   }

}

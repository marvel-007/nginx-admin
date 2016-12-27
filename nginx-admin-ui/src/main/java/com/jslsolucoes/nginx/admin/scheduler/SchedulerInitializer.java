/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.scheduler;

import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;
import com.jslsolucoes.nginx.admin.repository.impl.ConfigurationType;

import br.com.caelum.vraptor.events.VRaptorInitialized;

@ApplicationScoped
public class SchedulerInitializer {

	@Inject
	private ConfigurationRepository configurationRepository;

	public void init(@Observes VRaptorInitialized vRaptorInitialized) throws SchedulerException, SQLException {

		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobDetail job = JobBuilder.newJob(HttpRequestJob.class)
				.usingJobData("url_base", configurationRepository.getString(ConfigurationType.URL_BASE)).build();
		Trigger trigger = TriggerBuilder.newTrigger().startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(30).repeatForever()).build();
		scheduler.scheduleJob(job, trigger);
		scheduler.start();
	}

}

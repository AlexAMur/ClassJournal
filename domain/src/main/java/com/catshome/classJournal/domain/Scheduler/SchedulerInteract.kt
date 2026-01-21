package com.catshome.classJournal.domain.Scheduler

import javax.inject.Inject

class SchedulerInteract @Inject constructor(val repository: SchedulerRepository) {
    suspend fun save(){
      repository.getScheduler()
    }
}
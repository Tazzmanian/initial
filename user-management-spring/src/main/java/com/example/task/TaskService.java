/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import com.example.employee.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;

    public Task createTask(Task task) {
        return repo.save(task);
    }

    public Task getById(Long id) {
        return repo.findOne(id);
    }

    public Page<TaskUpdaterDTO> getAllTasks(Pageable pageRequest) {
        Page<Task> tasks = repo.findAll(pageRequest);
        return TaskUpdaterMapper.mapEntityPageIntoDTOPage(pageRequest, tasks);
    }

    public Page<TaskUpdaterDTO> getEmployeesTasks(List<Employee> employees, Pageable pageRequest) {
        Page<Task> tasks = repo.findByAssigneesIn(employees, pageRequest);
        return TaskUpdaterMapper.mapEntityPageIntoDTOPage(pageRequest, tasks);
    }

    public Page<TaskDTO> getByEmployeeId(Long id, Pageable pageRequest) {
        Page<Task> tasks = repo.findByAssigneesId(id, pageRequest);
        return TaskMapper.mapEntityPageIntoDTOPage(pageRequest, tasks);
    }

    public TaskDTO logWork(Long id, Update update, Employee employee) throws Exception {
        Task dbTask = repo.findOne(id);
        if (dbTask == null) {
            throw new Exception("Task not found");
        }
        update.setUpdater(employee);
        update.setTask(dbTask);
        dbTask.getUpdates().add(update);
        dbTask.setLastUpdated(employee);
        return TaskMapper.mapEntityIntoDTO(repo.save(dbTask));
    }
}

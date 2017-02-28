/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import com.example.employee.Employee;
import com.example.employee.EmployeeDTO;
import com.example.employee.EmployeeMapper;
import com.example.employee.EmployeeRepository;
import com.example.employee.EmployeeService;
import com.example.employer.Employer;
import com.example.employer.EmployerRepository;
import com.example.user.User;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;

    @Autowired
    private EmployerRepository employerRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private UpdateRepository updateRepo;

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

    public Task delete(String username, Long id) {

        Task task = repo.findByAssignerUserUserNameAndId(username, id);

        if (task == null) {
            return task;
        }

        Task temp = task;
        temp.setAssigner(null);
        temp.setAssignees(null);

        temp = repo.save(temp);

        repo.delete(temp);

        return task;
    }

    private List<Employee> showAssigneesOnTaskByEmployerHelper(Long taskId, Long employerId) {
        Employer employer = employerRepo.findOne(employerId);

//        employer.getEmployees().stream().forEach((employee) -> {
//            fillEmployee(employee);
//        });
//        and for(:) does not work
        for (int i = 0; i < employer.getEmployees().size(); ++i) {
            fillEmployee(employer.getEmployees().get(i));
        }

        List<Employee> employeeList = new ArrayList<>();
        employer = employerRepo.findOne(employerId);
        List<Employee> currentEmployeeList = employer.getEmployees();

        currentEmployeeList.stream().forEach((Employee employee) -> {
            employee.getTasks().stream().filter((task) -> (Objects.equals(task.getId(), taskId))).forEach((_item) -> {
                employeeList.add(employee);
            });
        });

        return employeeList;
    }

    public List<EmployeeDTO> showAssigneesOnTaskByEmployer(Long taskId, Long employerId) {
        return EmployeeMapper.mapEntitiesIntoDTOs(showAssigneesOnTaskByEmployerHelper(taskId, employerId));
    }

    private void fillEmployee(Employee empl) {
        User user = empl.getUser();

        if (empl.getDob() == null) {
            empl.setDob(user.getBirthDate());
        }
        if (empl.getFirstName() == null) {
            empl.setFirstName(user.getFirstName());
        }
        if (empl.getLastName() == null) {
            empl.setLastName(user.getLastName());
        }
        if (empl.getPhoneNumber() == null) {
            empl.setPhoneNumber(user.getPhoneNumber());
        }

        employeeRepo.save(empl);
    }

    public List<Update> showTasksUpdatesByEmployer(Long taskId, Long employerId) {

        List<Employee> employees = showAssigneesOnTaskByEmployerHelper(taskId, employerId);

        List<List<Update>> list = employees.stream().map(Employee::getUpdates).collect(Collectors.toList());

        List<Update> flattenList = list.stream().flatMap(List::stream).collect(Collectors.toList());

        List<Update> filteredList = flattenList.stream().filter(x -> taskId.equals(x.getTask().getId())).collect(Collectors.toList());

        return filteredList;
    }
}

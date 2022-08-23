package ru.shmvsky.dbaudit;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	private EmployeeRepository employeeRepository;

	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);

		if (employee.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(employee.get());
		}

		throw new EmployeeNotFoundException("Employee not found");

	}

	@PostMapping
	public ResponseEntity<Employee> saveEmployee(@RequestParam String name, @RequestParam Integer age) {
		
		Employee employee = Employee.builder()
			.name(name)
			.age(age)
			.build();
		
		Employee persistedEmployee = employeeRepository.save(employee);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(persistedEmployee);
	}

}

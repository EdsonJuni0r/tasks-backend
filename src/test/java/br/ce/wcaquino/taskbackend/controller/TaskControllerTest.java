package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {
	
	@Mock
	private TaskRepo TaskRepo;
	
	@InjectMocks
	private TaskController controller;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void naoDeveSalvarTerefaSemDescricao() {
		Task todo = new Task();
		todo.setDueDate(LocalDate.now());
		try {
			controller.save(todo);
			Assertions.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assertions.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task todo = new Task();
		todo.setTask("Descricao");
		try {
			controller.save(todo);
			Assertions.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assertions.assertEquals("Fill the due date", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task todo = new Task();
		todo.setTask("Descricao");
		todo.setDueDate(LocalDate.of(2020, 01 ,01));
		try {
			controller.save(todo);
			Assertions.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assertions.assertEquals("Due date must not be in past", e.getMessage());
		}
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task todo = new Task();
		todo.setTask("Descricao");
		todo.setDueDate(LocalDate.now());
		controller.save(todo);
		
		Mockito.verify(TaskRepo).save(todo);
	}
}

package org.example.expert.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.client.WeatherClient;
import org.example.expert.domain.common.dto.AuthUserDTO;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.dto.request.TodoSaveRequestDTO;
import org.example.expert.domain.todo.dto.response.TodoResponseDTO;
import org.example.expert.domain.todo.dto.response.TodoSaveResponseDTO;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponseDTO;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final WeatherClient weatherClient;

    @Transactional
    public TodoSaveResponseDTO saveTodo(AuthUserDTO authUser, TodoSaveRequestDTO todoSaveRequest) {
        User user = User.fromAuthUser(authUser);

        String weather = weatherClient.getTodayWeather();

        Todo newTodo = new Todo(
                todoSaveRequest.getTitle(),
                todoSaveRequest.getContents(),
                weather,
                user
        );
        Todo savedTodo = todoRepository.save(newTodo);

        return TodoSaveResponseDTO.createDTO(savedTodo, weather, user);
    }

    public Page<TodoResponseDTO> findAllTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Todo> todos = todoRepository.findAllByOrderByModifiedAtDesc(pageable);

        return todos.map(TodoResponseDTO::createDTO);
    }

    public TodoResponseDTO findTodoById(long todoId) {
        Todo todo = todoRepository.findByIdOrElseThrow(todoId);

        User user = todo.getUser();

        return TodoResponseDTO.createDTO(todo);
    }
}

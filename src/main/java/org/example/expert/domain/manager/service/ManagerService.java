package org.example.expert.domain.manager.service;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.common.dto.AuthUserDTO;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequestDTO;
import org.example.expert.domain.manager.dto.response.*;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.manager.repository.ManagerRepository;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

	private final ManagerRepository managerRepository;
	private final UserRepository userRepository;
	private final TodoRepository todoRepository;

	@Transactional
	public ManagerSaveResponseDTO saveManager(AuthUserDTO authUser, long todoId,
		ManagerSaveRequestDTO managerSaveRequestDTO) {
		// 일정을 만든 유저
		User user = User.fromAuthUser(authUser);
		Todo todo = todoRepository.findByIdOrElseThrow(todoId);

		checkEntityLinkage(user.getId(), todo.getUser().getId(), "해당 일정의 작성자가 아닙니다.");

		User managerUser = userRepository.findUserByIdOrElseThrow(managerSaveRequestDTO.getManagerUserId(),
			"등록하려고 하는 담당자 유저가 존재하지 않습니다.");

		checkSelfAssignment(user, managerUser);

		Manager newManagerUser = new Manager(managerUser, todo);
		Manager savedManagerUser = managerRepository.save(newManagerUser);

		return ManagerSaveResponseDTO.createDTO(savedManagerUser, managerUser);
	}

	public List<ManagerResponseDTO> getManagers(long todoId) {
		//테스트 코드 작성을 위해 해당 코드만 예외적으로 예외처리를 getManagers 메서드에서 구현함
		Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new InvalidRequestException("Todo not found"));

		List<Manager> managerList = managerRepository.findAllByTodoId(todo.getId());

		List<ManagerResponseDTO> dtoList = new ArrayList<>();
		for (Manager manager : managerList) {
			dtoList.add(ManagerResponseDTO.createDTO(manager));
		}
		return dtoList;
	}

	@Transactional
	public void deleteManager(long userId, long todoId, long managerId) {
		User user = userRepository.findUserByIdOrElseThrow(userId);
		Todo todo = todoRepository.findByIdOrElseThrow(todoId);

		validateTodoCreator(user, todo);

		Manager manager = managerRepository.findByIdOrElseThrow(managerId);

		checkEntityLinkage(todo.getId(), manager.getTodo().getId(), "해당 일정에 등록된 담당자가 아닙니다.");

		managerRepository.delete(manager);
	}

	private void checkEntityLinkage(Long id1, Long id2, String message) {
		if (!ObjectUtils.nullSafeEquals(id1, id2)) {
			throw new InvalidRequestException(message);
		}
	}

	private void validateTodoCreator(User user, Todo todo) {
		if (todo.getUser() == null || !ObjectUtils.nullSafeEquals(user.getId(), todo.getUser().getId())) {
			throw new InvalidRequestException("해당 일정을 만든 유저가 유효하지 않습니다.");
		}
	}

	private static void checkSelfAssignment(User user, User managerUser) {
		if (ObjectUtils.nullSafeEquals(user.getId(), managerUser.getId())) {
			throw new InvalidRequestException("일정 작성자는 본인을 담당자로 등록할 수 없습니다.");
		}
	}
}

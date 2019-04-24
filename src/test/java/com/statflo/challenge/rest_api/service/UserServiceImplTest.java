package com.statflo.challenge.rest_api.service;

import com.statflo.challenge.rest_api.Exceptions.InvalidUserCriteriaException;
import com.statflo.challenge.rest_api.Exceptions.UserAlreadyExistException;
import com.statflo.challenge.rest_api.entity.User;
import com.statflo.challenge.rest_api.enums.UserConstants;
import com.statflo.challenge.rest_api.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

    private String id = UUID.randomUUID().toString();
    private String name = "ABC";
    private String role = "MR";

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindUserById() {
        Optional<User> userOptional = Optional.of(getMockUser());
        when(userRepository.findById(id)).thenReturn(userOptional);
        Optional<User> result = userService.findUserById(id);
        assertEquals(userOptional, result);
    }

    @Test
    public void testFindUserById_WhenNoRecord() {
        Optional<User> userOptional = Optional.empty();
        when(userRepository.findById(id)).thenReturn(userOptional);
        Optional<User> result = userService.findUserById(id);
        assertEquals(userOptional, result);
    }

    @Test
    public void saveUser() {
        User user = getMockUserIdNull();
        doReturn(user).when(userRepository).save(user);
        User result = userService.saveUser(user);
        assertNotNull(result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getRole(), result.getRole());
    }

    @Test(expected = UserAlreadyExistException.class)
    public void saveUser_WhenUserAlreadyExist() {
        User user = getMockUser();
        doReturn(Optional.of(user)).when(userRepository).findById(user.getId());
        userService.saveUser(user);
    }

    @Test
    public void findByCriteria_ByRole_Name_Id() {
        User user = getMockUser();
        List<User> users = Collections.singletonList(user);
        Map<String, Object> columnsVsValue = getUserColumnVsValueMap(user);
        when(userRepository.findByIdAndNameAndRole(user.getId(), user.getName(), user.getRole())).thenReturn(users);
        List<User> result = userService.findByCriteria(columnsVsValue);
        assertEquals(users, result);
    }

    @Test
    public void findByCriteria_ByRole_Name() {
        User user = getMockUser();
        List<User> users = Collections.singletonList(user);
        Map<String, Object> columnVsValues = new HashMap<>();
        columnVsValues.put(UserConstants.ROLE.getValue(), user.getRole());
        columnVsValues.put(UserConstants.NAME.getValue(), user.getName());
        when(userRepository.findByNameAndRole(user.getName(), user.getRole())).thenReturn(users);
        List<User> result = userService.findByCriteria(columnVsValues);
        assertEquals(users, result);
    }

    @Test
    public void findByCriteria_ByRole_Id() {
        User user = getMockUser();
        List<User> users = Collections.singletonList(user);
        Map<String, Object> columnVsValues = new HashMap<>();
        columnVsValues.put(UserConstants.ID.getValue(), user.getId());
        columnVsValues.put(UserConstants.ROLE.getValue(), user.getRole());
        when(userRepository.findByIdAndRole(user.getId(), user.getRole())).thenReturn(users);
        List<User> result = userService.findByCriteria(columnVsValues);
        assertEquals(users, result);
    }

    @Test
    public void findByCriteria_ById_Name() {
        User user = getMockUser();
        List<User> users = Collections.singletonList(user);
        Map<String, Object> columnVsValues = new HashMap<>();
        columnVsValues.put(UserConstants.ID.getValue(), user.getId());
        columnVsValues.put(UserConstants.NAME.getValue(), user.getName());
        when(userRepository.findByIdAndName(user.getId(), user.getName())).thenReturn(users);
        List<User> result = userService.findByCriteria(columnVsValues);
        assertEquals(users, result);
    }

    @Test
    public void findByCriteria_ById() {
        User user = getMockUser();
        List<User> users = Collections.singletonList(user);
        Map<String, Object> columnVsValues = new HashMap<>();
        columnVsValues.put(UserConstants.ID.getValue(), user.getId());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        List<User> result = userService.findByCriteria(columnVsValues);
        assertEquals(users, result);
    }

    @Test
    public void findByCriteria_ById_WhenNoRecord() {
        User user = getMockUser();
        Map<String, Object> columnVsValues = new HashMap<>();
        columnVsValues.put(UserConstants.ID.getValue(), user.getId());
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        List<User> result = userService.findByCriteria(columnVsValues);
        assertTrue(result.isEmpty());
    }

    @Test
    public void findByCriteria_ByName() {
        User user = getMockUser();
        List<User> users = Collections.singletonList(user);
        Map<String, Object> columnVsValues = new HashMap<>();
        columnVsValues.put(UserConstants.NAME.getValue(), user.getName());
        when(userRepository.findByName(user.getName())).thenReturn(users);
        List<User> result = userService.findByCriteria(columnVsValues);
        assertEquals(users, result);
    }

    @Test
    public void findByCriteria_ByRole() {
        User user = getMockUser();
        List<User> users = Collections.singletonList(user);
        Map<String, Object> columnVsValues = new HashMap<>();
        columnVsValues.put(UserConstants.ROLE.getValue(), user.getRole());
        when(userRepository.findByRole(user.getRole())).thenReturn(users);
        List<User> result = userService.findByCriteria(columnVsValues);
        assertEquals(users, result);
    }

    @Test(expected = InvalidUserCriteriaException.class)
    public void findByCriteria_InvalidCriteria() {
        User user = getMockUser();
        Map<String, Object> columnVsValues = new HashMap<>();
        columnVsValues.put("address", user.getId());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.findByCriteria(columnVsValues);
    }

    @Test(expected = InvalidUserCriteriaException.class)
    public void findByCriteria_EmptyCriteria() {
        User user = getMockUser();
        Map<String, Object> columnVsValues = Collections.emptyMap();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.findByCriteria(columnVsValues);
    }

    private User getMockUser() {
        return new User(id, name, role);
    }

    private User getMockUserIdNull() {
        User user = new User(id, name, role);
        user.setId(null);
        return user;
    }

    private Map<String, Object> getUserColumnVsValueMap(User user) {
        Map<String, Object> columnVsValues = new HashMap<>();
        columnVsValues.put(UserConstants.ID.getValue(), user.getId());
        columnVsValues.put(UserConstants.ROLE.getValue(), user.getRole());
        columnVsValues.put(UserConstants.NAME.getValue(), user.getName());
        return columnVsValues;
    }
}
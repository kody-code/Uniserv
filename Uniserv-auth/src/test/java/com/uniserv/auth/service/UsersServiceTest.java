package com.uniserv.auth.service;

import cn.dev33.satoken.stp.StpUtil;
import com.uniserv.auth.mapper.UsersMapper;
import com.uniserv.auth.service.impl.UsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * <p>
 * 用户服务测试类
 * </p>
 *
 * @author kody
 * @since 2026-01-07
 */
@SpringBootTest
@TestConfiguration
@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersMapper baseMapper;

    @InjectMocks
    private UsersServiceImpl usersService;

    private String testUserId;
    private UUID testUUID;

    @BeforeEach
    void setup() {
        testUUID = UUID.randomUUID();
        testUserId = testUUID.toString();
    }

    @Test
    void getRoleList_WhenUserIsLoggedIn_ReturnsRoles() {
        // 准备测试数据
        List<String> expectedRoles = Arrays.asList("ADMIN", "USER");

        // 模拟登录状态和数据库查询结果
        when(StpUtil.isLogin()).thenReturn(true);
        when(baseMapper.getRoles(testUUID)).thenReturn(expectedRoles);

        // 执行测试
        List<String> actualRoles = usersService.getRoleList(testUserId);

        // 验证结果
        assertEquals(expectedRoles, actualRoles);
        verify(StpUtil.isLogin());
        verify(baseMapper).getRoles(testUUID);
    }

}

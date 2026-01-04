package com.uniserv;

import com.uniserv.common.utils.BCryptPasswordUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UniservApplicationTests {

    @Test
    void contextLoads() {
        String password = "$2a$10$2OiKgupzudFYjjDgksf8L.Bu3Ecx.TdBjq5gdhhG9t.y.nVgkCUv.";
        System.out.println(BCryptPasswordUtils.verify(password, "123456"));
    }

}

package com.uniserv.cipher.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uniserv.cipher.dto.request.PasswordInfoRequestDto;
import com.uniserv.cipher.entity.CipherPasswordInfo;

/**
 * <p>
 * 密码管理信息表（存储主密码相关） 服务类
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
public interface ICipherPasswordInfoService extends IService<CipherPasswordInfo> {

    /**
     * 判断密码信息是否存在
     *
     * @return 是否存在
     */
    boolean existsPasswordInfo();

    /**
     * 初始化密码信息
     *
     * @param requestDto 请求参数
     * @return 是否成功
     */
    boolean initPasswordInfo(PasswordInfoRequestDto requestDto) throws Exception;


    /**
     * 验证主密码
     *
     * @param requestDto 请求参数
     * @return 是否成功
     */
    boolean checkMasterPassword(PasswordInfoRequestDto requestDto) throws Exception;


    /**
     * 更新密码信息
     *
     * @param requestDto 密码信息
     * @return 是否成功
     */
    boolean updatePasswordInfo(PasswordInfoRequestDto requestDto) throws Exception;

    /**
     * 删除密码信息
     *
     * @return 是否成功
     */
    boolean deletePasswordInfo();

}

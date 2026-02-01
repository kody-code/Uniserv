package com.uniserv.cipher.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.cipher.dto.request.PasswordInfoRequestDto;
import com.uniserv.cipher.entity.CipherPasswordInfo;
import com.uniserv.cipher.mapper.CipherPasswordInfoMapper;
import com.uniserv.cipher.service.ICipherPasswordInfoService;
import com.uniserv.cipher.utils.PasswordEncryptUtil;
import com.uniserv.common.enums.ResultCode;
import com.uniserv.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 密码管理信息表（存储主密码相关） 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CipherPasswordInfoServiceImpl extends ServiceImpl<CipherPasswordInfoMapper, CipherPasswordInfo> implements ICipherPasswordInfoService {

    /**
     * 密码加密工具
     */
    private final PasswordEncryptUtil encryptUtil;

    /**
     * 判断密码信息是否存在
     *
     * @return 是否存在
     */
    @Override
    public boolean existsPasswordInfo() {
        // 判断是否登录
        if (StpUtil.isLogin()) {
            // 获取用户 ID
            String userId = StpUtil.getLoginIdAsString();
            CipherPasswordInfo info = getOne(new LambdaQueryWrapper<CipherPasswordInfo>().eq(CipherPasswordInfo::getUserId, UUID.fromString(userId)));
            return info != null;
        } else {
            log.error("请先登录");
            throw new BusinessException(ResultCode.AUTH_ERROR.getCode(), "请先登录");
        }
    }

    /**
     * 初始化密码信息
     *
     * @param requestDto 请求参数
     * @return 是否成功
     */
    @Override
    public boolean initPasswordInfo(PasswordInfoRequestDto requestDto) throws Exception {
        // 参数校验
        if (requestDto == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }

        // 判断密码信息是否存在
        if (!existsPasswordInfo()) {
            // 生成盐和验证哈希
            String verifySalt = encryptUtil.generateSalt();
            String verifyHash = encryptUtil.generateVerifyHash(requestDto.getMasterPassword(), verifySalt);
            String userId = StpUtil.getLoginIdAsString();
            // 创建密码信息
            CipherPasswordInfo passwordInfo = new CipherPasswordInfo();
            passwordInfo.setUserId(UUID.fromString(userId))
                    .setMasterVerifyHash(verifyHash)
                    .setVerifySalt(verifySalt);
            // 保存密码信息
            return save(passwordInfo);
        } else {
            throw new BusinessException(ResultCode.SYSTEM_ERROR.getCode(), "密码信息已存在");
        }
    }

    /**
     * 验证主密码
     *
     * @param requestDto 请求参数
     * @return 是否成功
     */
    @Override
    public boolean checkMasterPassword(PasswordInfoRequestDto requestDto) throws Exception {
        /*
         * TODO 多次验证密码，密码错误次数超过限制，则锁定用户
         *  密码错误次数超过限制，则锁定用户
         */

        // 参数校验
        if (requestDto == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }

        if (existsPasswordInfo()) {
            // 获取用户 ID
            String userId = StpUtil.getLoginIdAsString();
            // 获取密码信息
            CipherPasswordInfo info = getOne(new LambdaQueryWrapper<CipherPasswordInfo>().eq(CipherPasswordInfo::getUserId, UUID.fromString(userId)));
            // 验证主密码
            return encryptUtil.verifyMasterPassword(requestDto.getMasterPassword(), info.getMasterVerifyHash(), info.getVerifySalt());
        } else {
            throw new BusinessException(ResultCode.SYSTEM_ERROR.getCode(), "密码信息不存在");
        }
    }

    /**
     * 更新密码信息
     *
     * @param requestDto 密码信息
     * @return 是否成功
     */
    @Override
    public boolean updatePasswordInfo(PasswordInfoRequestDto requestDto) throws Exception {
        // 参数校验
        if (requestDto == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }

        // 获取用户 ID
        if (existsPasswordInfo()) {
            // 生成盐和验证哈希
            String verifySalt = encryptUtil.generateSalt();
            String verifyHash = encryptUtil.generateVerifyHash(requestDto.getMasterPassword(), verifySalt);

            // 获取用户 ID
            String userId = StpUtil.getLoginIdAsString();

            CipherPasswordInfo info = getOne(new LambdaQueryWrapper<CipherPasswordInfo>().eq(CipherPasswordInfo::getUserId, UUID.fromString(userId)));
            info.setVerifySalt(verifySalt)
                    .setMasterVerifyHash(verifyHash)
                    .setVersion(info.getVersion() + 1)
                    .setUpdatedAt(LocalDateTime.now());

            // 更新密码信息
            return updateById(info);

        } else {
            throw new BusinessException(ResultCode.SYSTEM_ERROR.getCode(), "密码信息不存在");
        }
    }

    /**
     * 删除密码信息
     *
     * @return 是否成功
     */
    @Override
    public boolean deletePasswordInfo() {
        // 获取用户 ID
        if (existsPasswordInfo()) {
            // 获取用户 ID
            String userId = StpUtil.getLoginIdAsString();

            return remove(new LambdaQueryWrapper<CipherPasswordInfo>().eq(CipherPasswordInfo::getUserId, UUID.fromString(userId)));

        } else {
            throw new BusinessException(ResultCode.SYSTEM_ERROR.getCode(), "密码信息不存在");
        }
    }
}

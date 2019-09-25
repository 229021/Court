package com.san.platform.innerlog.annotation;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.san.platform.config.IpUtil;
import com.san.platform.innerlog.Innerlog;
import com.san.platform.innerlog.InnerlogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect    //表示该类为一个切面类
@Component //@component组件扫描,让其 logService能注入进来
public class SystemLogAspect {
    @Reference
    private InnerlogService innerlogService;

    //本地异常日志记录对象
    private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);
    //Controller层切点
    @Pointcut("@annotation(com.san.platform.innerlog.annotation.MyLog)")
    public  void controllerAspect() {
    }

    //切面 配置通知
    @AfterReturning("controllerAspect()")
    public void saveSysLog(JoinPoint joinPoint) {

        //保存日志
        Innerlog innerlog = new Innerlog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            String value = myLog.value();//也就是方法体
            innerlog.setOpModel(value);//保存获取的操作
        }

        //获取请求的方法名
        String methodName = method.getName();
        //存储方法名
        innerlog.setOpType(methodName);
        //存储请求时间
        innerlog.setOpTime(new Date());

       //请求的参数
       Object[] args = joinPoint.getArgs();
       //将参数所在的数组转换成json
       String params = JSON.toJSONStringWithDateFormat(args,"yyyy-MM-dd HH:mm:ss");
       //存储请求参数（操作信息）
       innerlog.setOpInfo(params);
       //日志级别
       innerlog.setLogLevel("info");
       HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
       //获取用户ip地址
       String ipAddr = IpUtil.getIpAddr(request);
       innerlog.setClientIp(ipAddr);
       //获取用户名
//     Object username = request.getSession().getAttribute("username");
        String username = "admin";
       innerlog.setOpName(username);
       //获取角色权限
       innerlog.setOpRole("1");
//     Object username = request.getSession().getAttribute("username");
       //调用service保存innrtlog实体类到数据库
       innerlogService.createInnerlog(innerlog);
       //获取用户名
       //innerlog.setOpName(ShiroUtils.getUserEntity().getUsername());
    }



}

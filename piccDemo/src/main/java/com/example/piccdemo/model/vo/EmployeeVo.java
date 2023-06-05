package com.example.piccdemo.model.vo;//看包名就知道这个是hibernate附加的constraints

import com.example.piccdemo.model.po.Employee;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author Kern
 * @Title: Employee
 * @ProjectName kern-demo
 * @Description: TODO
 * @date 2019/9/1718:53
 */
public class EmployeeVo {

    @NotNull(message = "姓名必填!")
    @Length(max = 20, message = "姓名过长!")
    private String name;

    @NotNull(message = "工牌必填!")
    @Pattern(regexp = "^[0-9]\\d{9}",message = "请输入10位数字工牌!")//长度10，0-9
    private String badgeCode;

    @Pattern(regexp = "^[1-2]",message = "性别参数错误!")
    @NotNull(message = "性别必填!")
    private String gender;

    @Past(message = "无效的出生日期!")
    private Date birthDate;

    private int num;

    private String high;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBadgeCode() {
        return badgeCode;
    }

    public void setBadgeCode(String badgeCode) {
        this.badgeCode = badgeCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "EmployeeVo{" +
                "name='" + name + '\'' +
                ", badgeCode='" + badgeCode + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", high='" + high + '\'' +
                '}';
    }

    public static EmployeeVo toVo(Employee employee){
        EmployeeVo employeeVo = new EmployeeVo();
        BeanUtils.copyProperties(employee,employeeVo);
        return employeeVo;
    }
}

package org.aes.compare.uiconsole.business;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.abstracts.TerminalCommandProcessCheck;
import org.aes.compare.customterminal.business.concretes.TerminalCommandManager;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

public class UIConsoleDBServiceImplStudent implements TerminalCommandProcessCheck {
    private ORMConfigSingleton ormConfig = new ORMConfigSingleton();
    private final TerminalCommandManager tcm;
    private final TerminalCommandLayout layout;

    public UIConsoleDBServiceImplStudent(TerminalCommandManager tcm, TerminalCommandLayout layout) {
        this.tcm = tcm;
        this.layout = layout;
    }

    public void save() {
        Student student = new Student();
        System.out.println(ColorfulTextDesign.getInfoColorText("--> Student Save process is initialized"));

        System.out.print(ColorfulTextDesign.getTextForUserFeedback("Type name (String): "));
        String stringInput = SafeScannerInput.getStringInput(tcm);
        if (isCanceled()) return;
        student.setName(stringInput);


        System.out.print(ColorfulTextDesign.getTextForUserFeedback("Type Grade (int): "));
        int intInput = SafeScannerInput.getIntInput(tcm);
        if (isCanceled()) return;
        student.setGrade(intInput);


        Address address = tcm.displayAddressMenu.save();
        if (isCanceled()) return;
        student.setAddress(address);
        ormConfig.getStudentService().save(student);
        System.out.println(ColorfulTextDesign.getSuccessColorText("Student is saved: " + student));
    }


    public void findById() {
        //   return ormConfig.getStudentService().findById(id);
    }


    public void findAll() {
        //  return ormConfig.getStudentService().findAll();
    }


    public void findByStudentIdWithCourseName() {
        //  return ormConfig.getStudentService().findByStudentIdWithCourseName(studentId, courseName);
    }


    public void update() {
        //  ormConfig.getStudentService().update(s);
    }


    public void delete() {
        //   ormConfig.getStudentService().deleteById(id);
    }


    public void resetTable() {
        //ormConfig.getStudentService().resetTable();
    }

    @Override
    public boolean isCanceled() {
        System.out.println("isAllowed : "+tcm.isCurrentProcessCanceled());
        return tcm.isCurrentProcessCanceled();
    }
}

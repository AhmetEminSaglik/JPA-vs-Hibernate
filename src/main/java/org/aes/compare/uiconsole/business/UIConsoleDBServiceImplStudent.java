package org.aes.compare.uiconsole.business;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.abstracts.TerminalCommandProcessCheck;
import org.aes.compare.customterminal.business.concretes.TerminalCommandManager;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.utility.ColorfulTextDesign;

public class UIConsoleDBServiceImplStudent implements TerminalCommandProcessCheck {
    private final TerminalCommandManager tcm;
    private final TerminalCommandLayout layout;
    private final ORMConfigSingleton ormConfig = new ORMConfigSingleton();

    public UIConsoleDBServiceImplStudent(TerminalCommandManager tcm, TerminalCommandLayout layout) {
        this.tcm = tcm;
        this.layout = layout;
    }

    public void save() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE save  Student"));
        /*
        Student student = new Student();
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("--> Student Save process is initialized"));

        String inputMsg = ColorfulTextDesign.getTextForUserFeedback("Type name (String): ");
        String stringInput = SafeScannerInput.getStringInput(inputMsg, tcm);
        if (isCanceled()) return;
        student.setName(stringInput);


        inputMsg = ColorfulTextDesign.getTextForUserFeedback("Type Grade (int): ");
        int intInput = SafeScannerInput.getIntInput(inputMsg, tcm);
        if (isCanceled()) return;
        student.setGrade(intInput);


        Address address = tcm.displayAddressMenu.save();
        if (isCanceled()) return;
        student.setAddress(address);
        ormConfig.getStudentService().save(student);
        System.out.println(ColorfulTextDesign.getSuccessColorTextWithPrefix("Student is saved: " + student));
    */
    }


    public void findById() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE findById  Student"));
        //   return ormConfig.getStudentService().findById(id);
    }


    public void findAll() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE findAll  Student"));
        //  return ormConfig.getStudentService().findAll();
    }


    public void findByStudentIdWithCourseName() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE findByStudentIdWithCourseName  Student"));
        //  return ormConfig.getStudentService().findByStudentIdWithCourseName(studentId, courseName);
    }


    public void update() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE update  Student"));
        //  ormConfig.getStudentService().update(s);
    }


    public void delete() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE delete  Student"));
        //   ormConfig.getStudentService().deleteById(id);
    }


    public void resetTable() {
        //ormConfig.getStudentService().resetTable();
    }

    @Override
    public boolean isCanceled() {
        System.out.println("isAllowed : " + tcm.isAllowedCurrentProcess());
        return tcm.isAllowedCurrentProcess();
    }
}

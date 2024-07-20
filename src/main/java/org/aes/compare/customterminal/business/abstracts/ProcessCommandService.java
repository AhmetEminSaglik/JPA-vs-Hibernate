package org.aes.compare.customterminal.business.abstracts;

import org.aes.compare.customterminal.model.TerminalCMD;
import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;

public interface ProcessCommandService {

    //    void getCommand(String inputCommand);
    EnumCMDLineParserResult processCommand(String text);

    EnumCMDLineParserResult parseCommand(String text) throws Exception; // --> lower chars, long chars,

    String clearCommand(String text);

    TerminalCMD getDecidedTerminalCMD();
    /*
     * -c -S
     * create student || Student create --> kismina gidilir.
     *
     * Type for Student's Name:
     *  Ahmet
     * Type for Student's LastName (String):
     * Emin
     * Type for Student's grade (int):
     * 3
     * Type for Student's address (Address):
     *  1-) create Address
     *  2-) Not found any registered unassigned Address
     *  2-) List found registered unassigned Address (4 address)
     *
     *
     *       1-> tiklarsa address olusturucak  ve bu address donecek
     *
     * */

}

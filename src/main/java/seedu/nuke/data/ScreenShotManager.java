package seedu.nuke.data;

import com.alibaba.fastjson.JSON;
import seedu.nuke.data.storage.Decoder;
import seedu.nuke.data.storage.Encoder;
import seedu.nuke.data.storage.StorageManager;
import seedu.nuke.directory.Module;
import seedu.nuke.exception.CorruptedFileException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * a screen shot manager that manages all scree shots and deals with operations such as save screen shots and undo.
 */
public class ScreenShotManager {
    private static ArrayList<ScreenShot> screenShotList = new ArrayList<>();
    private static Stack<ScreenShot> undoStack = new Stack<>();
    private static int pointer = -1;

    private static ScreenShot popLastScreenShot() throws EmptyStackException {
        undoStack.pop();
        return undoStack.peek();
    }

    private static ScreenShot peekLastScreenShot() {
        return undoStack.peek();
    }

    /**
     * revert to the last changed state for moduleList for undo feature.
     *
     * @throws IOException exception is thrown when error occurred during IO operation.
     * @throws CorruptedFileException exception is thrown when converting a corrupted string to moduleList.
     * @throws EmptyStackException exception is thrown when user trying to undo at the initial state.
     */
    public static void revertToLastScreenShot() throws IOException, CorruptedFileException, EmptyStackException {
        ScreenShot lastScreenShot = popLastScreenShot();
        String moduleListString = lastScreenShot.getModuleListString();
        InputStream is = new ByteArrayInputStream(moduleListString.getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        ArrayList<Module> moduleList = new Decoder(bufferedReader).decode();
        ModuleManager.setModuleList(moduleList);
        bufferedReader.close();
    }

    /**
     * Save the moduleList as a string if it was changed.
     */
    public static void saveScreenShot() {
        String currentModuleListString = new Encoder(ModuleManager.getModuleList()).encode();
        ScreenShot screenShot = new ScreenShot(currentModuleListString);
        if (getScreenShotSize() == 0) {
            undoStack.push(screenShot);
            return;
        }
        ScreenShot lastScreenShot = peekLastScreenShot();
        String lastModuleListString = lastScreenShot.getModuleListString();
        if (!lastModuleListString.equals(currentModuleListString)) {
            undoStack.push(screenShot);
        }
    }

    private static int getScreenShotSize() {
        return undoStack.size();
    }

    public static ArrayList<ScreenShot> getScreenShotList() {
        return ScreenShotManager.screenShotList;
    }

    public static void setScreenShotList(ArrayList<ScreenShot> screenShotList) {
        ScreenShotManager.screenShotList = screenShotList;
    }

    public static int getCurrentPointer() {
        return ScreenShotManager.pointer;
    }

    public static void setCurrentPointer(int pointer) {
        ScreenShotManager.pointer = pointer;
    }

    /**
     * return a scree shot with certain index.
     *
     * @return a screen shot
     */
    public static ScreenShot getCurrentScreenShot() {
        if (getCurrentPointer() < 0) {
            return screenShotList.get(0);
        }
        return screenShotList.get(getCurrentPointer());
    }

    public static void movePointerForward() {
        ScreenShotManager.pointer++;
    }

    /**
     * change to another screen shot.
     */
    public static void movePointerBackWard() {
        if (ScreenShotManager.pointer > 0) {
            ScreenShotManager.pointer--;
        }
    }

    public static void saveNewScreenShot(ScreenShot toAdd) {
        movePointerForward();
        screenShotList.add(toAdd);
    }

    public static void cutTailNodes() {
        screenShotList = (ArrayList<ScreenShot>) screenShotList.subList(0, pointer);
    }

    /**
     * save the screen shot.
     */
    public static ScreenShot takeNewScreenShot() {
        String jsonStrModuleList = StorageManager.saveModuleToString(ModuleManager.getModuleList());
        return new ScreenShot(jsonStrModuleList);
    }

    /**
     * set module manager data according to screen shot manager data.
     */
    public static ArrayList<Module> readFromScreenShot(String jsonStr) {
        return (ArrayList<Module>) JSON.parseArray(jsonStr, Module.class);
    }
}

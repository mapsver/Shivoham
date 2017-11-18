package com.atmavedagana.shivoham.shivoham;

/**
 * Created by shiv on 11/18/2017.
 */

class GlobalSettingsSingleton {

    
    enum MODE_STATE { LISTEN_MODE, READ_MODE, CHANT_MODE }
    enum USER_ROLE { ADMIN, DEBUG, SCRIBE, STUDENT }

    private static MODE_STATE mUseModeState;
    private static USER_ROLE mUserRole;
    private static boolean mSuppressRepeatAudioInChantMode;

    static MODE_STATE getmUseModeState() {
        return mUseModeState;
    }
    static void setmUseModeState(MODE_STATE useModeState) {
        mUseModeState = useModeState;
    }
    public static USER_ROLE getmUserRole() {
        return mUserRole;
    }
    static void setmUserRole(USER_ROLE userRole) {
        mUserRole = userRole;
    }
    public static boolean ismSuppressRepeatAudioInChantMode() {
        return mSuppressRepeatAudioInChantMode;
    }
    public static void setmSuppressRepeatAudioInChantMode(boolean suppressRepeatAudioInChantMode) {
        GlobalSettingsSingleton.mSuppressRepeatAudioInChantMode = suppressRepeatAudioInChantMode;
    }

    private GlobalSettingsSingleton() {}
    private static class GlobalSettingsSingletonHolder {
        static final GlobalSettingsSingleton instance = new GlobalSettingsSingleton();
    }    
    static GlobalSettingsSingleton getInstance() {        
        return GlobalSettingsSingletonHolder.instance;
    }

    static void setDefaults() {
        setmUseModeState(MODE_STATE.CHANT_MODE);
        setmUserRole(USER_ROLE.STUDENT);
        setmSuppressRepeatAudioInChantMode(false);
    }
}

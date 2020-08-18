package com.blibli.future.phase2.controller;

public class ApiPath {
    public static final String BASE_PATH = "/api";

    /**
     * AUTH PATH
     */
    public static final String AUTH = BASE_PATH + "/auth";
    public static final String AUTH_LOGIN = AUTH + "/login";
    public static final String AUTH_REGISTER = AUTH + "/register";
    public static final String AUTH_CHANGE_PASSWORD = AUTH + "/password";

    /**
     * ADMIN PATH
     */
    public static final String BASE_ADMIN = BASE_PATH + "/admin";

    // Batch
    public static final String ADMIN_BATCH = BASE_ADMIN + "/batch";
    public static final String ADMIN_GET_ALL_BATCH_TRAINING = BASE_ADMIN + "/batchtraining";
    public static final String ADMIN_BATCH_CREATE = ADMIN_BATCH + "";
    public static final String ADMIN_BATCH_GET_ALL = ADMIN_BATCH + "";
    public static final String ADMIN_BATCH_GET_BY_ID = ADMIN_BATCH + "/detail";
    public static final String ADMIN_BATCH_DELETE = ADMIN_BATCH + "";

    // Employee
    public static final String ADMIN_EMPLOYEE = BASE_ADMIN + "/employee";
    public static final String ADMIN_EMPLOYEE_CREATE = ADMIN_EMPLOYEE + "";
    public static final String ADMIN_EMPLOYEE_GET_ALL = ADMIN_EMPLOYEE + "";
    public static final String ADMIN_EMPLOYEE_GET_ALL_BY_BATCH = ADMIN_EMPLOYEE + "/batch";
    public static final String ADMIN_EMPLOYEE_GET_BY_ID = ADMIN_EMPLOYEE + "/detail";
    public static final String ADMIN_EMPLOYEE_UPDATE = ADMIN_EMPLOYEE + "";
    public static final String ADMIN_EMPLOYEE_DELETE = ADMIN_EMPLOYEE + "";
    public static final String ADMIN_EMPLOYEE_RESET_PASSWORD = ADMIN_EMPLOYEE + "/reset";

    // Training
    public static final String ADMIN_TRAINING = ADMIN_BATCH + "/training";
    public static final String ADMIN_TRAINING_CREATE = ADMIN_TRAINING + "";
    public static final String ADMIN_TRAINING_GET_ALL = ADMIN_TRAINING + "";
    public static final String ADMIN_TRAINING_GET_ATTENDANCE = ADMIN_TRAINING + "/attendance";
    public static final String ADMIN_TRAINING_GET_BY_ID = ADMIN_TRAINING + "/detail";
    public static final String ADMIN_TRAINING_UPDATE = ADMIN_TRAINING + "";
    public static final String ADMIN_TRAINING_DELETE = ADMIN_TRAINING + "";

    // Trainer
    public static final String ADMIN_TRAINER = BASE_ADMIN + "/trainer";
    public static final String ADMIN_TRAINER_CREATE = ADMIN_TRAINER + "";
    public static final String ADMIN_TRAINER_GET_ALL = ADMIN_TRAINER + "";
    public static final String ADMIN_TRAINER_GET_BY_ID = ADMIN_TRAINER + "/detail";
    public static final String ADMIN_TRAINER_UPDATE = ADMIN_TRAINER + "";
    public static final String ADMIN_TRAINER_DELETE = ADMIN_TRAINER + "";
    public static final String ADMIN_TRAINER_RESET_PASSWORD = ADMIN_TRAINER + "/reset";

    // Material
    public static final String ADMIN_MATERIAL = BASE_ADMIN + "/material";
    public static final String ADMIN_MATERIAL_CREATE = ADMIN_MATERIAL + "";
    public static final String ADMIN_MATERIAL_GET_ALL = ADMIN_MATERIAL + "";
    public static final String ADMIN_MATERIAL_DELETE = ADMIN_MATERIAL + "";
    public static final String ADMIN_MATERIAL_FILE_UPLOAD = ADMIN_MATERIAL + "/file";

    // Test
    public static final String ADMIN_TEST = BASE_ADMIN + "/test";
    public static final String ADMIN_TEST_CREATE = ADMIN_TEST + "";
    public static final String ADMIN_TEST_GET_MATERIAL_TEST = ADMIN_TEST + "";
    public static final String ADMIN_TEST_GET_QUESTION = ADMIN_TEST + "/preview";
    public static final String ADMIN_TEST_UPDATE = ADMIN_TEST + "";

    // Notification
    public static final String ADMIN_NOTIFICATION = BASE_ADMIN + "/notification";
    public static final String ADMIN_NOTIFICATION_CREATE = ADMIN_NOTIFICATION + "";
    public static final String ADMIN_NOTIFICATION_GET_ALL = ADMIN_NOTIFICATION + "";

    /**
     * TRAINER PATH
     */

    public static final String BASE_TRAINER = BASE_PATH + "/trainer";

    // Material
    public static final String TRAINER_MATERIAL = BASE_TRAINER + "/material";
    public static final String TRAINER_MATERIAL_CREATE = TRAINER_MATERIAL + "";
    public static final String TRAINER_MATERIAL_DELETE = TRAINER_MATERIAL + "";

    // Test
    public static final String TRAINER_TEST = BASE_TRAINER + "/test";
    public static final String TRAINER_TEST_CREATE = TRAINER_TEST + "";
    public static final String TRAINER_TEST_GET_QUESTION = TRAINER_TEST + "/preview";
    public static final String TRAINER_TEST_UPDATE = TRAINER_TEST + "";

    // Training
    public static final String TRAINER_TRAINING = BASE_TRAINER + "/training";
    public static final String TRAINER_TRAINING_GET_ALL = TRAINER_TRAINING + "";
    public static final String TRAINER_TRAINING_GET_BY_ID = TRAINER_TRAINING + "/detail";

    /**
     * EMPLOYEE PATH
     */
    public static final String BASE_EMPLOYEE = BASE_PATH + "/employee";
    public static final String EMPLOYEE_GET_BY_ID = BASE_EMPLOYEE + "";

    // Material
    public static final String EMPLOYEE_MATERIAL = BASE_EMPLOYEE + "/material";
    public static final String EMPLOYEE_MATERIAL_GET_ALL = EMPLOYEE_MATERIAL + "";

    // Notification
    public static final String EMPLOYEE_NOTIFICATION = BASE_EMPLOYEE + "/notification";
    public static final String EMPLOYEE_NOTIFICATION_GET_ALL = EMPLOYEE_NOTIFICATION + "";
    public static final String EMPLOYEE_NOTIFICATION_CHECK = EMPLOYEE_NOTIFICATION + "/trigger";
    public static final String EMPLOYEE_NOTIFICATION_READ = EMPLOYEE_NOTIFICATION + "";

    // Training
    public static final String EMPLOYEE_TRAINING = BASE_EMPLOYEE + "/training";
    public static final String EMPLOYEE_TRAINING_GET_ALL = EMPLOYEE_TRAINING + "";
    public static final String EMPLOYEE_TRAINING_SUBMIT_ATTENDANCE = EMPLOYEE_TRAINING + "/attendance";
    public static final String EMPLOYEE_TRAINING_CHECK_ATTENDANCE = EMPLOYEE_TRAINING + "/attendance";
    public static final String EMPLOYEE_TRAINING_GET_BY_ID = EMPLOYEE_TRAINING + "/detail";

    // Test
    public static final String EMPLOYEE_TEST = BASE_EMPLOYEE + "/test";
}

CREATE DATABASE IF NOT EXISTS sms CHARACTER
SET
    utf8mb4 COLLATE utf8mb4_unicode_ci;

USE sms;

CREATE TABLE
    IF NOT EXISTS sms_class (
        id BIGINT NOT NULL PRIMARY KEY,
        class_code CHAR(4) NOT NULL,
        class_college VARCHAR(20) NOT NULL,
        class_description VARCHAR(100) NULL,
        head_teacher_id BIGINT NOT NULL,
        created_by BIGINT NOT NULL,
        updated_by BIGINT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
        is_deleted TINYINT (1) DEFAULT 0 NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS sms_class_course (
        id BIGINT NOT NULL PRIMARY KEY,
        course_id BIGINT NOT NULL,
        class_id BIGINT NOT NULL,
        created_by BIGINT NOT NULL,
        updated_by BIGINT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
        is_deleted TINYINT (1) DEFAULT 0 NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS sms_class_member (
        id BIGINT NOT NULL PRIMARY KEY,
        class_id INT NOT NULL,
        student_id BIGINT NOT NULL,
        created_by BIGINT NOT NULL,
        updated_by BIGINT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
        is_deleted TINYINT (1) DEFAULT 0 NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS sms_course (
        id BIGINT NOT NULL PRIMARY KEY,
        is_elective TINYINT (1) NOT NULL,
        credits TINYINT NOT NULL,
        teacher_id BIGINT NOT NULL,
        created_by BIGINT NOT NULL,
        updated_by BIGINT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
        is_deleted TINYINT (1) DEFAULT 0 NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS sms_forum (
        id BIGINT NOT NULL PRIMARY KEY,
        parent_id BIGINT DEFAULT -1 NOT NULL,
        is_anonymous TINYINT (1) DEFAULT 0 NOT NULL,
        content TEXT NOT NULL,
        likes INT DEFAULT 0 NOT NULL,
        created_by BIGINT NOT NULL,
        updated_by BIGINT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
        is_deleted TINYINT (1) DEFAULT 0 NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS sms_grade (
        id BIGINT NOT NULL PRIMARY KEY,
        regular_score DECIMAL(5, 2) NOT NULL,
        exam_score DECIMAL(5, 2) NOT NULL,
        pass_threshold DECIMAL(5, 2) DEFAULT 50.00 NOT NULL,
        created_by BIGINT NOT NULL,
        updated_by BIGINT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
        is_deleted TINYINT (1) DEFAULT 0 NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS sms_notification (
        id BIGINT NOT NULL PRIMARY KEY,
        title VARCHAR(100) NOT NULL,
        content TEXT NULL,
        created_by BIGINT NOT NULL,
        updated_by BIGINT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
        is_deleted TINYINT (1) DEFAULT 0 NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS sms_user (
        id BIGINT NOT NULL PRIMARY KEY,
        account VARCHAR(255) NOT NULL,
        name VARCHAR(8) NOT NULL,
        password VARCHAR(255) NOT NULL,
        email VARCHAR(255) NULL,
        phone VARCHAR(20) NULL,
        id_card VARCHAR(20) NULL,
        location VARCHAR(255) NULL,
        gender INT NULL,
        age INT NULL,
        role INT NULL,
        avatar VARCHAR(255) NULL,
        is_ban TINYINT (1) DEFAULT 0 NULL,
        created_by BIGINT NULL,
        updated_by BIGINT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL,
        is_deleted TINYINT (1) DEFAULT 0 NULL
    );
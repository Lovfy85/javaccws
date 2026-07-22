CREATE DATABASE IF NOT EXISTS clothing_capsule;

USE clothing_capsule;


CREATE TABLE users (

    id VARCHAR(50) PRIMARY KEY,

    username VARCHAR(50) NOT NULL UNIQUE,

    password_hash VARCHAR(255) NOT NULL,

    name VARCHAR(100) NOT NULL,

    preferred_style VARCHAR(50),

    color_preference VARCHAR(50)

);


CREATE TABLE clothing_items (

    id VARCHAR(50) PRIMARY KEY,

    user_id VARCHAR(50) NOT NULL,

    category VARCHAR(20) NOT NULL,

    name VARCHAR(100) NOT NULL,

    color VARCHAR(50) NOT NULL,

    brand VARCHAR(100) NOT NULL,

    image_path VARCHAR(255),

    style VARCHAR(50),


    -- Top specific field
    sleeve_type VARCHAR(50),


    -- Bottom specific field
    fit_type VARCHAR(50),


    -- Footwear specific field
    footwear_type VARCHAR(50),


    CONSTRAINT fk_clothing_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE

);


CREATE TABLE outfits (

    id VARCHAR(50) PRIMARY KEY,

    user_id VARCHAR(50) NOT NULL,

    top_id VARCHAR(50) NOT NULL,

    bottom_id VARCHAR(50) NOT NULL,

    footwear_id VARCHAR(50) NOT NULL,

    score INT DEFAULT 0,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,


    CONSTRAINT unique_saved_outfit
        UNIQUE(user_id, top_id, bottom_id, footwear_id),


    CONSTRAINT fk_outfit_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,


    CONSTRAINT fk_outfit_top
        FOREIGN KEY (top_id)
        REFERENCES clothing_items(id)
        ON DELETE CASCADE,


    CONSTRAINT fk_outfit_bottom
        FOREIGN KEY (bottom_id)
        REFERENCES clothing_items(id)
        ON DELETE CASCADE,


    CONSTRAINT fk_outfit_footwear
        FOREIGN KEY (footwear_id)
        REFERENCES clothing_items(id)
        ON DELETE CASCADE

);
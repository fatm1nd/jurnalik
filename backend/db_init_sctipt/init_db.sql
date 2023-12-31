CREATE TABLE IF NOT EXISTS full_users_ids (
    user_id INT PRIMARY KEY,
    vk_id INT,
    telegram_id INT,
    instagram_id INT
);

CREATE TABLE IF NOT EXISTS raw_posts(
    post_id VARCHAR,
    user_id INT,
    group_id BIGINT,
    PRIMARY KEY (post_id, user_id)
);

CREATE TABLE IF NOT EXISTS prepared_posts(
    post_id VARCHAR,
    user_id INT,
    group_id BIGINT,
    category VARCHAR,
    PRIMARY KEY (post_id, user_id)
);

CREATE TABLE IF NOT EXISTS items(
    item VARCHAR PRIMARY KEY,
    post_id VARCHAR,
    type VARCHAR
);

CREATE TABLE IF NOT EXISTS groups_and_channels(
    group_id INT PRIMARY KEY,
    group_name VARCHAR,
    picture VARCHAR,
    source VARCHAR
);

CREATE TABLE IF NOT EXISTS telegram_channels(
    group_id BIGINT,
    username VARCHAR,
    user_id INT
);

CREATE TABLE IF NOT EXISTS vk_table(
    vk_id INT PRIMARY KEY,
    vk_token VARCHAR 
);

CREATE TABLE IF NOT EXISTS full_users_ids (
    user_id INT PRIMARY KEY,
    vk_id INT,
    telegram_id INT,
    instagram_id INT
)

CREATE TABLE IF NOT EXISTS raw_posts(
    post_id INT,
    user_id INT,
    group_id BIGINT,
    PRIMARY KEY (post_id, user_id)
)

CREATE TABLE IF NOT EXISTS items(
    item VARCHAR PRIMARY KEY,
    post_id INT,
    type VARCHAR
)

CREATE TABLE IF NOT EXISTS groups_an_channels(
    group_id INT PRIMARY KEY,
    group_name varchar,
    picture varchar,
    source varchar
)
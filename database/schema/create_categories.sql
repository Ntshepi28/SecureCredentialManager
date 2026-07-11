CREATE TABLE IF NOT EXISTS categories (
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    name VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,


    CONSTRAINT fk_categories_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE,

    CONSTRAINT uq_user_category
    UNIQUE (user_id, name)
)
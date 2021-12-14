CREATE TABLE IF NOT EXISTS users (
            id SERIAL PRIMARY KEY,
            nickname text UNIQUE,
            fullname VARCHAR(255),
            about TEXT,
            email text UNIQUE,
            count_view_profile INT
        );
CREATE INDEX IF NOT EXISTS users_id ON users USING HASH (id);
CREATE INDEX IF NOT EXISTS users_email ON users USING HASH (email);

CREATE TABLE IF NOT EXISTS forums (
            id SERIAL PRIMARY KEY,
            author_id INT,
            title VARCHAR(255),
            slug text UNIQUE,
			FOREIGN KEY (author_id) REFERENCES users (id)
        );
CREATE INDEX IF NOT EXISTS forums_slug ON forums USING HASH (slug);

CREATE TABLE IF NOT EXISTS forum_users (
            id SERIAL PRIMARY KEY,
			forum_id INT,
			user_id INT,
			UNIQUE(forum_id, user_id),
			FOREIGN KEY (forum_id) REFERENCES forums (id),
			FOREIGN KEY (user_id) REFERENCES users (id)
		);
CREATE INDEX IF NOT EXISTS forum_users_forum_id ON forum_users (forum_id);

CREATE TABLE IF NOT EXISTS threads (
            id SERIAL PRIMARY KEY,
            forum_id INT,
            title VARCHAR(255),
            author_id INT,
            message TEXT,
            created TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
            votes INT DEFAULT 0,
            slug text,
			FOREIGN KEY (forum_id) REFERENCES forums (id),
			FOREIGN KEY (author_id) REFERENCES users (id)
        );
CREATE INDEX IF NOT EXISTS threads_slug ON threads USING HASH (slug);
CREATE INDEX IF NOT EXISTS threads_forum ON threads USING HASH (forum_id);
CREATE INDEX IF NOT EXISTS threads_forum_created ON threads (forum_id, created);

CREATE TABLE IF NOT EXISTS posts (
            id SERIAL PRIMARY KEY,
            parent INT,
			path INT[],
            author_id INT,
            message TEXT,
            modified TIMESTAMP WITH TIME ZONE DEFAULT NULL,
            forum_id INT,
            thread_id INT,
            created TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
			FOREIGN KEY (author_id) REFERENCES users (id),
			FOREIGN KEY (forum_id) REFERENCES forums (id),
			FOREIGN KEY (thread_id) REFERENCES threads (id)
        );
CREATE INDEX IF NOT EXISTS post_thread ON posts (thread_id);
CREATE INDEX IF NOT EXISTS post_thread_id ON posts (thread_id, id);
CREATE INDEX IF NOT EXISTS post_thread_parent_path2 ON posts (thread_id, parent, (path[2]));
CREATE INDEX IF NOT EXISTS post_path2 on posts ((path[2]));
CREATE INDEX IF NOT EXISTS post_path2_path ON posts ((path[2]) DESC, path ASC);
CREATE INDEX IF NOT EXISTS post_path ON posts (path ASC);

CREATE TABLE IF NOT EXISTS thread_votes (
			id SERIAL PRIMARY KEY,
			thread_id INT,
			user_id INT,
			voice INT,
			FOREIGN KEY (thread_id) REFERENCES threads (id),
			FOREIGN KEY (user_id) REFERENCES users (id)
		);
CREATE INDEX IF NOT EXISTS thread_votes_thread_nickname ON thread_votes (thread_id, user_id);

CREATE TABLE IF NOT EXISTS statistics (
			count_users INT,
			count_forums INT,
			count_threads INT,
            most_popular_user INT,
            most_viewed_profile INT,
            FOREIGN KEY (most_popular_user) REFERENCES users (id),
            FOREIGN KEY (most_viewed_profile) REFERENCES users (id)
		);

CREATE OR REPLACE FUNCTION update_path()
			RETURNS TRIGGER
			AS $update_path$
		DECLARE
BEGIN
			NEW.path = array_append(COALESCE((SELECT path FROM posts WHERE id = NEW.parent), ARRAY[0]), NEW.id);
RETURN NEW;
END;
		$update_path$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS posts_path ON posts;
CREATE TRIGGER posts_path BEFORE INSERT ON posts
    FOR EACH ROW
    EXECUTE PROCEDURE update_path();
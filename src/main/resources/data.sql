-- 初期ユーザー
INSERT INTO users (name, email, password, bio, icon_image, created_at, updated_at)
VALUES 

('テスト1', 'test@z.com', 'testuser123', 'テストユーザー1です', '/images/icon5.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 初期投稿
INSERT INTO posts (user_id, post, created_at, updated_at)
VALUES 
(30, 'Miyazaki kazuki', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 初期フォロー関係
INSERT INTO follows (following_id, followed_id, created_at, updated_at)
VALUES 

(5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
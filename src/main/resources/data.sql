-- （再起動のたびにクリーンにしたい場合は以下を有効化）
-- SET FOREIGN_KEY_CHECKS=0;
-- TRUNCATE TABLE follows;
-- TRUNCATE TABLE posts;
-- TRUNCATE TABLE users;
-- SET FOREIGN_KEY_CHECKS=1;

-- -------------------------
-- 初期ユーザー
-- -------------------------
INSERT INTO users (name, email, password, bio, icon_image, created_at, updated_at)
VALUES
('テスト1', 'test@a.com', 'testuser123', 'テストユーザー1です', 'icon1.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト2', 'test@b.com', 'testuser123', 'テストユーザー2です', 'icon2.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト3', 'test@c.com', 'testuser123', 'テストユーザー3です', 'icon3.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト4', 'test@d.com', 'testuser123', 'テストユーザー4です', 'icon4.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト5', 'test@e.com', 'testuser123', 'テストユーザー5です', 'icon5.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- -------------------------
-- 初期投稿
-- -------------------------
INSERT INTO posts (user_id, post, created_at, updated_at)
VALUES
(1, '初めまして！テスト1です', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, '今日もいい天気ですね', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'テスト2からの投稿です', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'こんにちは、テスト3です', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'テスト4の初投稿！', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'テスト5です。よろしくお願いします。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- -------------------------
-- 初期フォロー関係
-- follower_id が「フォローする側」
-- followed_id が「フォローされる側」
-- -------------------------
INSERT INTO follows (follower_id, followed_id, created_at, updated_at) VALUES
(1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ユーザー1 が ユーザー2 をフォロー
(2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ユーザー2 が ユーザー3 をフォロー
(3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ユーザー3 が ユーザー1 をフォロー
(4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ユーザー4 が ユーザー1 をフォロー
(5, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- ユーザー5 が ユーザー2 をフォロー
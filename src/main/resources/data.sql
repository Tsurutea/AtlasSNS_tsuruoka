<<<<<<< HEAD
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
=======
-- （再起動のたびに投入するなら最初にクリーン）
-- SET FOREIGN_KEY_CHECKS=0;
-- TRUNCATE TABLE follows;
-- TRUNCATE TABLE posts;
-- TRUNCATE TABLE users;
-- SET FOREIGN_KEY_CHECKS=1;

-- 初期ユーザー（email は全部ユニーク OK）
INSERT INTO users (name, email, password, bio, icon_image, created_at, updated_at)
VALUES
('テスト1', 'test@a.com', 'testuser123', 'テストユーザー1です', 'icon1.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト2', 'test@b.com', 'testuser123', 'テストユーザー1です', 'icon2.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト3', 'test@c.com', 'testuser123', 'テストユーザー1です', 'icon3.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト4', 'test@d.com', 'testuser123', 'テストユーザー1です', 'icon4.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト5', 'test@e.com', 'testuser123', 'テストユーザー1です', 'icon5.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト6', 'test@f.com', 'testuser123', 'テストユーザー1です', 'icon6.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト7', 'test@g.com', 'testuser123', 'テストユーザー1です', 'icon7.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト8', 'test@h.com', 'testuser123', 'テストユーザー1です', 'icon1.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト9', 'test@i.com', 'testuser123', 'テストユーザー1です', 'icon2.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト10', 'test@j.com', 'testuser123', 'テストユーザー1です', 'icon3.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト11', 'test@k.com', 'testuser123', 'テストユーザー1です', 'icon4.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト12', 'test@l.com', 'testuser123', 'テストユーザー1です', 'icon5.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト13', 'test@m.com', 'testuser123', 'テストユーザー1です', 'icon6.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト14', 'test@n.com', 'testuser123', 'テストユーザー1です', 'icon7.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト15', 'test@o.com', 'testuser123', 'テストユーザー1です', 'icon1.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト16', 'test@p.com', 'testuser123', 'テストユーザー1です', 'icon2.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト17', 'test@q.com', 'testuser123', 'テストユーザー1です', 'icon3.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト18', 'test@r.com', 'testuser123', 'テストユーザー1です', 'icon4.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト19', 'test@s.com', 'testuser123', 'テストユーザー1です', 'icon5.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト20', 'test@t.com', 'testuser123', 'テストユーザー1です', 'icon6.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト21', 'test@u.com', 'testuser123', 'テストユーザー1です', 'icon7.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト22', 'test@w.com', 'testuser123', 'テストユーザー1です', 'icon1.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト23', 'test@x.com', 'testuser123', 'テストユーザー2です', 'icon2.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト24', 'test@y.com', 'testuser123', 'テストユーザー1です', 'icon3.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト25', 'test@z.com', 'testuser123', 'テストユーザー1です', 'icon4.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('テスト26', 'test@v.com', 'testuser123', 'テストユーザー1です', 'icon5.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 初期投稿
INSERT INTO posts (user_id, post, created_at, updated_at)
VALUES
(1, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'へい', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'へい', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, '初めまして', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 初期フォロー関係（末尾のカンマなし／(2,2) を削除）
-- A) みんなが user_id=2 をフォローしている
INSERT INTO follows (following_id, followed_id, created_at, updated_at) VALUES
  (1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (5, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (6, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (7, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (8, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (9, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- B) user_id=2 がいろんな人をフォローしている
INSERT INTO follows (following_id, followed_id, created_at, updated_at) VALUES
  (2, 1,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (2, 3,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (2, 4,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (2, 5,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (2, 6,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (2, 7,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (2, 8,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (2, 9,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (2, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (2, 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

>>>>>>> 2f38405 (提出)

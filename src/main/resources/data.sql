-- categoriesテーブルにレコード追加
INSERT INTO categories (id, name) VALUES (1, '食料品');
INSERT INTO categories (id, name) VALUES (2, '文房具');
INSERT INTO categories (id, name) VALUES (3, '書籍');

-- items_with_categoriesテーブルにレコード追加
INSERT INTO items_with_categories (id, name, price, category_id)
VALUES (1, '梨', 500, 1);

INSERT INTO items_with_categories (id, name, price, category_id)
VALUES (2, 'マッキー', 900, 2);

INSERT INTO items_with_categories (id, name, price, category_id)
VALUES (3, 'Cytech参考書', 300, 3);

-- 次に新規登録するときはIDを4から使う
ALTER SEQUENCE seq_items_with_categories_gen RESTART WITH 4;
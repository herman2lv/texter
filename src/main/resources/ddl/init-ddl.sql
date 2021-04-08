DROP database IF EXISTS gift_certificates;
CREATE database gift_certificates;
USE gift_certificates;

CREATE TABLE gift_certificate
(
    id               BIGINT                             NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name             VARCHAR(50)                        NOT NULL,
    description      VARCHAR(300)                       NOT NULL,
    price            DECIMAL(8, 2)                      NOT NULL,
    duration         INT                                NOT NULL,
    create_date      DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted          BOOLEAN  DEFAULT FALSE             NOT NULL
);

CREATE TABLE tag
(
    id      BIGINT                NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(50)           NOT NULL,
    deleted BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE gift_certificate_tag
(
    gift_certificate_id BIGINT,
    tag_id              BIGINT,
    PRIMARY KEY (gift_certificate_id, tag_id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id)
);

CREATE TRIGGER last_update_trigger
    BEFORE UPDATE
    ON gift_certificate
    FOR EACH ROW SET NEW.last_update_date = CURRENT_TIMESTAMP;

CREATE TABLE user
(
    id         BIGINT                NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email      VARCHAR(50)           NOT NULL,
    password   VARCHAR(50)           NOT NULL,
    first_name VARCHAR(50)           NOT NULL,
    last_name  VARCHAR(50)           NOT NULL,
    deleted    BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE status
(
    id   TINYINT     NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE item_order
(
    id          BIGINT                             NOT NULL PRIMARY KEY AUTO_INCREMENT,
    total_cost  DECIMAL(8, 2)                      NOT NULL,
    user_id     BIGINT                             NOT NULL,
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status_id   TINYINT                            NOT NULL,
    deleted     BOOLEAN  DEFAULT FALSE             NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (status_id) REFERENCES status (id)
);

CREATE TABLE order_details
(
    order_id            BIGINT        NOT NULL,
    gift_certificate_id BIGINT        NOT NULL,
    number_of_items     INT           NOT NULL,
    item_cost           DECIMAL(8, 2) NOT NULL,
    PRIMARY KEY (order_id, gift_certificate_id),
    FOREIGN KEY (order_id) REFERENCES item_order (id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id)
);

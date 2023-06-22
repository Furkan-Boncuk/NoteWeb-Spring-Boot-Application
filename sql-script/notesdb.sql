create table note(
                     notes_id serial primary key,
                     notes_title varchar(50) not null,
                     notes_category varchar(50) not null,
                     notes_created_date timestamp default current_timestamp,
                     notes_updated_date timestamp default current_timestamp,
                     note_content text
);

ALTER TABLE note
ALTER COLUMN notes_title TYPE varchar(100),
ALTER COLUMN notes_category TYPE varchar(100);

ALTER TABLE note
ALTER COLUMN notes_created_date DROP default,
ALTER COLUMN notes_updated_date DROP default;

ALTER TABLE note
ALTER COLUMN notes_created_date TYPE timestamp without time zone USING to_timestamp(notes_created_date::text, 'YYYY/MM/DD'),
ALTER COLUMN notes_updated_date TYPE timestamp without time zone USING to_timestamp(notes_updated_date::text, 'YYYY/MM/DD');

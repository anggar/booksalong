CREATE TABLE "tracker" (
  created_at TIMESTAMP WITHOUT TIME ZONE,
  updated_at TIMESTAMP WITHOUT TIME ZONE,
  current_page NUMERIC NOT NULL,
  user_id bigint NOT NULL REFERENCES public.user(id),
  book_id bigint NOT NULL REFERENCES book(id)
);

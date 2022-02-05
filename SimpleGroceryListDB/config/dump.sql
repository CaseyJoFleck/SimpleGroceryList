--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1 (Debian 14.1-1.pgdg110+1)
-- Dumped by pg_dump version 14.1 (Debian 14.1-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: groceryitem; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.groceryitem (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.groceryitem OWNER TO postgres;

--
-- Data for Name: groceryitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.groceryitem (id, name) FROM stdin;
0	dog food
\.


--
-- Name: groceryitem groceryitem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groceryitem
    ADD CONSTRAINT groceryitem_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--


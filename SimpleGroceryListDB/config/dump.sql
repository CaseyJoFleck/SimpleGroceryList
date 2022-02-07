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

--
-- Name: grocery_item_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.grocery_item_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.grocery_item_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: grocery_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.grocery_item (
                                     id integer DEFAULT nextval('public.grocery_item_sequence'::regclass) NOT NULL,
                                     name character varying(100) NOT NULL,
                                     quantity integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.grocery_item OWNER TO postgres;

--
-- Data for Name: grocery_item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.grocery_item (id, name, quantity) FROM stdin;
\.


--
-- Name: grocery_item_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.grocery_item_sequence', 1, false);


--
-- Name: grocery_item grocery_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grocery_item
    ADD CONSTRAINT grocery_item_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--


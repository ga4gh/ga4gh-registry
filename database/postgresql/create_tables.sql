--
-- PostgreSQL database dump from PRD instance
--

CREATE SCHEMA ga4gh_schema;

ALTER SCHEMA ga4gh_schema OWNER TO ga4gh_db_writer;

CREATE TYPE ga4gh_schema.implementation_category AS ENUM (
    'implementation',
    'deployment'
);

ALTER TYPE ga4gh_schema.implementation_category OWNER TO ga4gh_db_writer;

SET default_tablespace = '';

CREATE TABLE ga4gh_schema.implementation (
    id text NOT NULL,
    standard_version_id text NOT NULL,
    name text NOT NULL,
    description text,
    organization_id text NOT NULL,
    contact_url text,
    documentation_url text,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    environment text,
    version text NOT NULL,
    url text,
    category ga4gh_schema.implementation_category NOT NULL,
    curie_prefix text
);

ALTER TABLE ga4gh_schema.implementation OWNER TO ga4gh_db_writer;

CREATE TABLE ga4gh_schema.organization (
    id text NOT NULL,
    name text NOT NULL,
    short_name text,
    url text NOT NULL
);

ALTER TABLE ga4gh_schema.organization OWNER TO ga4gh_db_writer;

CREATE TABLE ga4gh_schema.release_status (
    id text NOT NULL,
    status text NOT NULL
);

ALTER TABLE ga4gh_schema.release_status OWNER TO ga4gh_db_writer;

CREATE TABLE ga4gh_schema.standard (
    id text NOT NULL,
    name text NOT NULL,
    standard_category_id text NOT NULL,
    summary text NOT NULL,
    description text NOT NULL,
    documentation_url text NOT NULL,
    release_status_id text NOT NULL,
    artifact text,
    abbreviation text,
    work_stream_id text NOT NULL
);

ALTER TABLE ga4gh_schema.standard OWNER TO ga4gh_db_writer;

CREATE TABLE ga4gh_schema.standard_category (
    id text NOT NULL,
    category text NOT NULL
);

ALTER TABLE ga4gh_schema.standard_category OWNER TO ga4gh_db_writer;

CREATE TABLE ga4gh_schema.standard_version (
    id text NOT NULL,
    standard_id text NOT NULL,
    version_number text NOT NULL,
    documentation_url text NOT NULL,
    release_status_id text NOT NULL
);

ALTER TABLE ga4gh_schema.standard_version OWNER TO ga4gh_db_writer;

CREATE TABLE ga4gh_schema.work_stream (
    id text NOT NULL,
    name text NOT NULL,
    abbreviation text
);

ALTER TABLE ga4gh_schema.work_stream OWNER TO ga4gh_db_writer;

ALTER TABLE ONLY ga4gh_schema.implementation
    ADD CONSTRAINT implementation_curie_prefix_key UNIQUE (curie_prefix);

ALTER TABLE ONLY ga4gh_schema.implementation
    ADD CONSTRAINT implementation_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ga4gh_schema.organization
    ADD CONSTRAINT organization_name_key UNIQUE (name);

ALTER TABLE ONLY ga4gh_schema.organization
    ADD CONSTRAINT organization_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ga4gh_schema.organization
    ADD CONSTRAINT organization_short_name_key UNIQUE (short_name);

ALTER TABLE ONLY ga4gh_schema.standard_category
    ADD CONSTRAINT standard_category_category_key UNIQUE (category);

ALTER TABLE ONLY ga4gh_schema.standard_category
    ADD CONSTRAINT standard_category_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ga4gh_schema.standard
    ADD CONSTRAINT standard_name_key UNIQUE (name);

ALTER TABLE ONLY ga4gh_schema.standard
    ADD CONSTRAINT standard_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ga4gh_schema.release_status
    ADD CONSTRAINT standard_status_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ga4gh_schema.release_status
    ADD CONSTRAINT standard_status_status_key UNIQUE (status);

ALTER TABLE ONLY ga4gh_schema.standard_version
    ADD CONSTRAINT standard_version_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ga4gh_schema.work_stream
    ADD CONSTRAINT work_stream_abbreviation_key UNIQUE (abbreviation);

ALTER TABLE ONLY ga4gh_schema.work_stream
    ADD CONSTRAINT work_stream_name_key UNIQUE (name);

ALTER TABLE ONLY ga4gh_schema.work_stream
    ADD CONSTRAINT work_stream_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ga4gh_schema.implementation
    ADD CONSTRAINT implementation_organization_id_fkey FOREIGN KEY (organization_id) REFERENCES ga4gh_schema.organization(id) ON UPDATE CASCADE;

ALTER TABLE ONLY ga4gh_schema.implementation
    ADD CONSTRAINT implementation_standard_version_id_fkey FOREIGN KEY (standard_version_id) REFERENCES ga4gh_schema.standard_version(id) ON UPDATE CASCADE;

ALTER TABLE ONLY ga4gh_schema.standard
    ADD CONSTRAINT standard_release_status_id_fkey FOREIGN KEY (release_status_id) REFERENCES ga4gh_schema.release_status(id) ON UPDATE CASCADE;

ALTER TABLE ONLY ga4gh_schema.standard
    ADD CONSTRAINT standard_standard_category_id_fkey FOREIGN KEY (standard_category_id) REFERENCES ga4gh_schema.standard_category(id) ON UPDATE CASCADE;

ALTER TABLE ONLY ga4gh_schema.standard_version
    ADD CONSTRAINT standard_version_release_status_id_fkey FOREIGN KEY (release_status_id) REFERENCES ga4gh_schema.release_status(id) ON UPDATE CASCADE;

ALTER TABLE ONLY ga4gh_schema.standard_version
    ADD CONSTRAINT standard_version_standard_id_fkey FOREIGN KEY (standard_id) REFERENCES ga4gh_schema.standard(id) ON UPDATE CASCADE;

ALTER TABLE ONLY ga4gh_schema.standard
    ADD CONSTRAINT standard_work_stream_id_fkey FOREIGN KEY (work_stream_id) REFERENCES ga4gh_schema.work_stream(id) ON UPDATE CASCADE;
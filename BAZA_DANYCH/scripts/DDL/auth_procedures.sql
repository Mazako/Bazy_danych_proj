CREATE OR REPLACE PROCEDURE remove_expired_tokens()
LANGUAGE plpgsql
AS
$$
BEGIN
    DELETE FROM verification_token
    WHERE expiration_date <= current_timestamp;
END;
$$
from sqlalchemy import create_engine
from db.database import Base  # Adjusted import statement

def init_db():
    SQLALCHEMY_DATABASE_URL = "postgresql://postgres:apples2apples@localhost/shopmood"
    engine = create_engine(SQLALCHEMY_DATABASE_URL)

    # Import all the models
    from db import models  # Adjusted import statement

    # Create the tables
    Base.metadata.create_all(bind=engine)

if __name__ == "__main__":
    init_db()

from sqlalchemy.orm import Session
from db.database import SessionLocal, engine
from db.models import Product

def populate_db():
    # Create a new session
    db = SessionLocal()

    # Define your products
    products = [
            {"emotion": "happy", "category": "food", "product": "Ice cream"},
            {"emotion": "sad", "category": "movie", "product": "Comedy movie"},
            {"emotion": "angry", "category": "food", "product": "Spicy food"},
            {"emotion": "surprised", "category": "movie", "product": "Thriller movie"},
            {"emotion": "neutral", "category": "food", "product": "Salad"},
            # Add more products as needed
        ]

    # Insert each product into the database
    for product in products:
        db_product = Product(**product)
        db.add(db_product)

    # Commit the changes
    db.commit()

    # Close the session
    db.close()

if __name__ == "__main__":
    populate_db()

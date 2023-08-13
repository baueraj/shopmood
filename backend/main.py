from fastapi import FastAPI
from pydantic import BaseModel
import base64
import io
from PIL import Image
from emotion_recognition import EmotionRecognizer
from sqlalchemy.orm import Session
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from db.models import Product

DATABASE_URL = "postgresql://postgres:apples2apples@localhost/shopmood"

engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

app = FastAPI()

class ImageData(BaseModel):
    image: str

@app.post("/recognize_emotion")
async def recognize_emotion(image_data: ImageData):
    # Decode the image
    image_bytes = base64.b64decode(image_data.image)
    image = Image.open(io.BytesIO(image_bytes))

    # TODO: Use a machine learning model to recognize the emotion
    emotion = "happy"  # Placeholder

    # Generate product suggestions based on the recognized emotion
    product_suggestions = generate_product_suggestions(emotion)

    return {"emotion": emotion, "product_suggestions": product_suggestions}

def generate_product_suggestions(emotion):
    # Hardcoded product suggestions
    product_suggestions = {
        "happy": ["Comedy movie", "Ice cream", "Party game"],
        "sad": ["Drama movie", "Comfort food", "Self-help book"],
        "angry": ["Action movie", "Workout equipment", "Stress ball"],
        "surprised": ["Mystery movie", "Surprise box", "Magic tricks book"],
        "neutral": ["Documentary", "Coffee", "Newspaper"],
    }

    return product_suggestions.get(emotion, [])

def get_suggested_products(emotion: str):
    # Create a new session
    session = SessionLocal()
    # Query the database for the products associated with the given emotion
    products = session.query(Product).filter(Product.emotion == emotion).all()
    # Close the session
    session.close()
    # Return the products
    return products

recognizer = EmotionRecognizer('./models/AffectNet_6336.h5')
@app.post("/recognize_emotion")
async def recognize_emotion(file: UploadFile = File(...)):
    # Save the file to a temporary location
    temp_file = "temp.jpg"
    with open(temp_file, "wb") as buffer:
        buffer.write(file.file.read())
    # Use the model to predict the emotion
    emotion = recognizer.predict_emotion(temp_file)
    # Get the suggested products for the recognized emotion
    products = get_suggested_products(emotion)
    return {"emotion": emotion, "suggested_products": products}

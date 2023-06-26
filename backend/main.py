from fastapi import FastAPI
from pydantic import BaseModel
import base64
import io
from PIL import Image

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

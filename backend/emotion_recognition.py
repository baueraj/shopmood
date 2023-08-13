from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.image import img_to_array, load_img
import numpy as np

class EmotionRecognizer:
    def __init__(self, model_path):
        self.model = load_model(model_path)

    def preprocess_image(self, image_path):
        # Load the image
        image = load_img(image_path, target_size=(224, 224))
        # Convert the image pixels to a numpy array
        image = img_to_array(image)
        # Reshape data for the model
        image = np.expand_dims(image, axis=0)
        # Prepare the image for the CNN Model model
        image = image.astype('float32')
        image = image / 255.0
        return image

    def predict_emotion(self, image_path):
        image = self.preprocess_image(image_path)
        # Predict the probability across all output classes
        yhat = self.model.predict(image)
        # Convert probabilities to class labels
        emotion = np.argmax(yhat)
        return emotion

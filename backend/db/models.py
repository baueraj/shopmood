from sqlalchemy import Column, Integer, String
from .database import Base

class Product(Base):
    __tablename__ = "products"

    id = Column(Integer, primary_key=True, index=True)
    emotion = Column(String, index=True)
    category = Column(String, index=True)
    product = Column(String)

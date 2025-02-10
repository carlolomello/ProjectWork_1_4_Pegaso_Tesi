import React, { useState, useEffect, useCallback, useRef } from 'react';
import './Carousel.css'; // Importa il CSS specifico per il componente

const Carousel = ({ items, imagesPath }) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isHovered, setIsHovered] = useState(false);
  const carouselRef = useRef(null);

  const handlePrev = useCallback(() => {
    setCurrentIndex((prevIndex) => (prevIndex === 0 ? items.length - 1 : prevIndex - 1));
  }, [items.length]);

  const handleNext = useCallback(() => {
    setCurrentIndex((prevIndex) => (prevIndex + 1 >= items.length ? 0 : prevIndex + 1));
  }, [items.length]);

  useEffect(() => {
    const interval = setInterval(() => {
      if (!isHovered) {
        handleNext();
      }
    }, 5000);

    return () => clearInterval(interval);
  }, [isHovered, handleNext]);

  return (
    <div
      className="carousel"
      ref={carouselRef}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <button className="carousel-btn prev" onClick={handlePrev}>←</button>
      <div className="carousel-container">
        {items.length > 0 && (
          <div
            className="carousel-item"
            style={{ backgroundImage: `url(${imagesPath}${items[currentIndex].developerName}.png)` }}
          >
            <h3>{items[currentIndex].label}</h3>
            <p>{items[currentIndex].description}</p>
          </div>
        )}
      </div>
      <button className="carousel-btn next" onClick={handleNext}>→</button>
    </div>
  );
};

export default Carousel;

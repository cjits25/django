document.addEventListener('DOMContentLoaded', () => {
  const apiKey = 'f0c4d53e17dfb685a9aa5cebabd90eff'; // Replace with your OpenWeatherMap API key
  const getWeatherButton = document.getElementById('getWeather');
  const cityInput = document.getElementById('city');
  const weatherGraph = document.getElementById('weatherGraph').getContext('2d');

  // Function to fetch weather data using async/await
  const fetchWeatherData = async (city, callback) => {
    const apiUrl = `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${apiKey}&units=metric`;
    try {
      const response = await axios.get(apiUrl);
      callback(null, response.data); // Use callback to pass data
    } catch (error) {
      callback(error, null); // Handle errors in the callback
    }
  };

  // Function to update the graph
  const updateGraph = (data) => {
    const temperature = data.main.temp;
    const feelsLike = data.main.feels_like;
    const humidity = data.main.humidity;

    new Chart(weatherGraph, {
      type: 'bar',
      data: {
        labels: ['Temperature (Â°C)', 'Feels Like (Â°C)', 'Humidity (%)'],
        datasets: [
          {
            label: `Weather Data for ${data.name}`,
            data: [temperature, feelsLike, humidity],
            backgroundColor: ['#36A2EB', '#FF6384', '#FFCD56'],
          },
        ],
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  };

  // Event listener for button click
  getWeatherButton.addEventListener('click', () => {
    const city = cityInput.value.trim();
    if (!city) {
      alert('Please enter a city name');
      return;
    }

    // Fetch weather data and update the graph
    fetchWeatherData(city, (error, data) => {
      if (error) {
        console.error('Error fetching weather data:', error.message);
        alert('Unable to fetch weather data. Please try again.');
      } else {
        updateGraph(data);
      }
    });
  });
});
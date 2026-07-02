const API_BASE_URL = '/api/v1';
// DOM Elements
const loginBtn = document.getElementById('loginBtn');
const userInfo = document.getElementById('userInfo');
const loginModal = document.getElementById('loginModal');
const closeModalBtn = document.getElementById('closeModalBtn');
const loginForm = document.getElementById('loginForm');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
const usernameInput = document.getElementById('username');
const loginError = document.getElementById('loginError');
const submitLoginBtn = document.getElementById('submitLoginBtn');
const submitBtnText = document.getElementById('submitBtnText');
const trendingGrid = document.getElementById('trendingGrid');
const signupFields = document.getElementById('signupFields');
const toggleAuthMode = document.getElementById('toggleAuthMode');
const modalTitle = document.querySelector('.modal-header h2');
const searchInput = document.getElementById('searchInput');

// State
let currentUser = null;
let isLoginMode = true;

// Initialize
document.addEventListener('DOMContentLoaded', () => {
  setupEventListeners();
  
  // Check for saved login session
  const savedToken = localStorage.getItem('pulse_token');
  if (savedToken) {
    currentUser = { token: savedToken };
    fetchTrendingSongs();
    
    // Hide login button, show user info
    loginBtn.classList.add('hidden');
    userInfo.classList.remove('hidden');
  }
  
  // Handle routes
  handleRouting();
});

function handleRouting() {
  const path = window.location.pathname;
  if (path === '/register') {
    // Open modal in sign up mode
    loginModal.classList.remove('hidden');
    isLoginMode = false;
    modalTitle.textContent = 'Sign up for Pulse';
    submitBtnText.textContent = 'Sign Up';
    signupFields.classList.remove('hidden');
    toggleAuthMode.innerHTML = `Already have an account? <span>Log in</span>`;
  } else if (path === '/login') {
    // Open modal in login mode
    loginModal.classList.remove('hidden');
    isLoginMode = true;
    modalTitle.textContent = 'Log in to Pulse';
    submitBtnText.textContent = 'Log In';
    signupFields.classList.add('hidden');
    toggleAuthMode.innerHTML = `Don't have an account? <span>Sign up</span>`;
  }
}

function setupEventListeners() {
  loginBtn.addEventListener('click', () => {
    window.history.pushState({}, '', '/login');
    isLoginMode = true;
    modalTitle.textContent = 'Log in to Pulse';
    submitBtnText.textContent = 'Log In';
    signupFields.classList.add('hidden');
    toggleAuthMode.innerHTML = `Don't have an account? <span>Sign up</span>`;
    loginModal.classList.remove('hidden');
  });

  closeModalBtn.addEventListener('click', () => {
    window.history.pushState({}, '', '/');
    loginModal.classList.add('hidden');
    loginError.classList.add('hidden');
  });

  // Close modal when clicking outside
  loginModal.addEventListener('click', (e) => {
    if (e.target === loginModal) {
      window.history.pushState({}, '', '/');
      loginModal.classList.add('hidden');
    }
  });

  toggleAuthMode.addEventListener('click', () => {
    isLoginMode = !isLoginMode;
    if (isLoginMode) {
      window.history.pushState({}, '', '/login');
      modalTitle.textContent = 'Log in to Pulse';
      submitBtnText.textContent = 'Log In';
      signupFields.classList.add('hidden');
      toggleAuthMode.innerHTML = `Don't have an account? <span>Sign up</span>`;
    } else {
      window.history.pushState({}, '', '/register');
      modalTitle.textContent = 'Sign up for Pulse';
      submitBtnText.textContent = 'Sign Up';
      signupFields.classList.remove('hidden');
      toggleAuthMode.innerHTML = `Already have an account? <span>Log in</span>`;
    }
    loginError.classList.add('hidden');
  });

  loginForm.addEventListener('submit', handleAuth);

  // User profile dropdown / logout logic
  userInfo.addEventListener('click', () => {
    if (confirm('Are you sure you want to log out?')) {
      localStorage.removeItem('pulse_token');
      window.location.href = '/';
    }
  });

  // Search logic
  let searchTimeout;
  searchInput.addEventListener('input', (e) => {
    clearTimeout(searchTimeout);
    const query = e.target.value.trim();
    
    if (query.length === 0) {
      if (currentUser) fetchTrendingSongs();
      return;
    }
    
    searchTimeout = setTimeout(() => {
      performSearch(query);
    }, 500); // Debounce 500ms
  });
}

async function performSearch(query) {
  if (!currentUser) return;
  
  trendingGrid.innerHTML = `
    <div class="skeleton-card"></div>
    <div class="skeleton-card"></div>
    <div class="skeleton-card"></div>
    <div class="skeleton-card"></div>
  `;
  document.querySelector('.section-header h2').textContent = `Search results for "${query}"`;
  
  try {
    const response = await fetch(`${API_BASE_URL}/search?q=${encodeURIComponent(query)}&type=song`, {
      headers: {
        'Authorization': `Bearer ${currentUser.token}`
      }
    });
    
    if (!response.ok) throw new Error('Failed to fetch search results');
    
    const results = await response.json();
    
    // The search response might return an object like { songs: [...] } or just an array depending on backend
    const songsList = results.songs || results.data || (Array.isArray(results) ? results : []);
    
    if (songsList.length === 0) {
        trendingGrid.innerHTML = '<p style="color: var(--text-muted); grid-column: 1/-1;">No results found.</p>';
        return;
    }
    
    renderSongs(songsList, true);
  } catch (error) {
    console.error('Search error:', error);
    trendingGrid.innerHTML = '<p style="color: var(--text-muted); grid-column: 1/-1;">Failed to load search results.</p>';
  }
}

async function handleAuth(e) {
  e.preventDefault();
  
  const email = emailInput.value;
  const password = passwordInput.value;
  const username = usernameInput?.value || '';
  
  if (!email || !password || (!isLoginMode && !username)) return;

  // UI state
  submitBtnText.classList.add('hidden');
  submitLoginBtn.querySelector('.loader').classList.remove('hidden');
  submitLoginBtn.disabled = true;
  loginError.classList.add('hidden');

  try {
    const endpoint = isLoginMode ? '/auth/login' : '/auth/register';
    const body = isLoginMode 
      ? { email, password }
      : { email, password, username, code: '1234' }; // Dummy code for register, assuming it bypasses if mocked

    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(body)
    });

    if (!response.ok) {
      throw new Error(isLoginMode ? 'Login failed' : 'Sign up failed');
    }

    const data = await response.json();
    
    // Success
    currentUser = data;
    localStorage.setItem('pulse_token', data.token);
    loginModal.classList.add('hidden');
    loginBtn.classList.add('hidden');
    
    // Update user info
    userInfo.classList.remove('hidden');
    if (data.profilePic) {
      userInfo.querySelector('.avatar').src = data.profilePic;
    }
    const userNameDisplay = document.getElementById('userNameDisplay');
    if (userNameDisplay && data.username) {
        userNameDisplay.textContent = data.username;
    }
    
    // Show toast or welcome message here
    console.log('Successfully logged in!', data);

    // Fetch songs now that we have the token
    fetchTrendingSongs();

  } catch (error) {
    console.error('Login error:', error);
    loginError.textContent = error.message;
    loginError.classList.remove('hidden');
  } finally {
    submitBtnText.classList.remove('hidden');
    submitLoginBtn.querySelector('.loader').classList.add('hidden');
    submitLoginBtn.disabled = false;
  }
}

async function fetchTrendingSongs() {
  if (!currentUser || !currentUser.token) {
    trendingGrid.innerHTML = '<p style="color: var(--text-muted); grid-column: 1/-1;">Please log in to view trending songs.</p>';
    return;
  }

  try {
    document.querySelector('.section-header h2').textContent = 'Trending Now';
    const response = await fetch(`${API_BASE_URL}/trending?country=IN`, {
      headers: {
        'Authorization': `Bearer ${currentUser.token}`
      }
    });
    
    if (!response.ok) throw new Error('Failed to fetch trending songs');
    
    const songs = await response.json();
    renderSongs(songs);
  } catch (error) {
    console.error('Error fetching trending songs:', error);
    trendingGrid.innerHTML = '<p style="color: var(--text-muted); grid-column: 1/-1;">Could not load trending songs. The server might be unavailable.</p>';
  }
}

function renderSongs(songs) {
  trendingGrid.innerHTML = '';
  
  if (!songs || songs.length === 0) {
    trendingGrid.innerHTML = '<p style="color: var(--text-muted); grid-column: 1/-1;">No trending songs found.</p>';
    return;
  }
  
  songs.slice(0, 10).forEach(song => {
    const card = document.createElement('div');
    card.className = 'music-card';
    card.onclick = () => playSong(song);
    
    card.innerHTML = `
      <div class="card-img-wrapper">
        <img src="${getImageUrl(song)}" alt="${song.name || song.title || 'Song'}">
        <div class="play-overlay">
          <i class="fa-solid fa-play"></i>
        </div>
      </div>
      <h3 class="card-title">${song.name || song.title || 'Unknown'}</h3>
      <p class="card-subtitle">${getArtistName(song)}</p>
    `;
    
    trendingGrid.appendChild(card);
  });
}

// Player State
let currentAudio = null;
let isPlaying = false;
let progressInterval;

function getImageUrl(song) {
  if (song.image && Array.isArray(song.image) && song.image.length > 0) {
    return song.image[song.image.length - 1].url;
  }
  return song.albumArt || song.thumbnail || 'https://images.unsplash.com/photo-1614680376573-df3480f0c6ff?q=80&w=300&auto=format&fit=crop';
}

function getArtistName(song) {
  if (song.artists && song.artists.primary && song.artists.primary.length > 0) {
    return song.artists.primary.map(a => a.name).join(', ');
  }
  return song.artist || song.artist_name || 'Unknown Artist';
}

async function resolveSongSource(song) {
  // If it already has download URLs or a direct preview URL
  if (song.downloadUrl && Array.isArray(song.downloadUrl) && song.downloadUrl.length > 0) {
    const urlObj = song.downloadUrl.find(u => u.quality === '320kbps') || song.downloadUrl[song.downloadUrl.length - 1];
    return urlObj.url;
  }
  if (song.previewUrl || song.streamUrl || song.url) {
    const u = song.previewUrl || song.streamUrl || song.url;
    if (u.endsWith('.mp4') || u.endsWith('.mp3') || u.endsWith('.m4a') || u.includes('aac') || u.includes('saavncdn')) return u;
  }
  
  // If it has a source field that looks like a stream
  if (song.source && (song.source.includes('saavncdn.com') || song.source.endsWith('.mp3') || song.source.endsWith('.mp4') || song.source.endsWith('.m4a'))) {
      return song.source;
  }

  // Fallback to fetch from JioSaavn unofficial API worker (like Android does)
  if (song.id) {
    try {
      const response = await fetch(`/api/external/songs/${song.id}`);
      const json = await response.json();
      if (json.success && json.data && json.data.length > 0) {
        const downloadUrls = json.data[0].downloadUrl;
        if (downloadUrls && downloadUrls.length > 0) {
          const bestUrlObj = downloadUrls.find(u => u.quality === '320kbps') || downloadUrls[downloadUrls.length - 1];
          return bestUrlObj.url;
        }
      }
    } catch (e) {
      console.error("Failed to resolve audio URL:", e);
    }
  }

  return null;
}

async function playSong(song) {
  const playerBar = document.getElementById('playerBar');
  const playerTitle = document.getElementById('playerTitle');
  const playerArtist = document.getElementById('playerArtist');
  const playerImage = document.getElementById('playerImage');
  const playPauseBtn = document.getElementById('playPauseBtn');
  const progressBar = document.querySelector('.progress-bar .progress');
  const timeDisplay = document.querySelectorAll('.progress-container .time');
  
  playerTitle.textContent = song.name || song.title || 'Unknown Title';
  playerArtist.textContent = getArtistName(song);
  playerImage.src = getImageUrl(song);
  
  playerBar.classList.remove('hidden');
  playPauseBtn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i>'; // Loading state
  
  // Logic to actually play audio
  if (currentAudio) {
    currentAudio.pause();
    clearInterval(progressInterval);
  }
  
  // Resolve URL asynchronously
  const audioUrl = await resolveSongSource(song);
  
  if (audioUrl && typeof audioUrl === 'string') {
    currentAudio = new Audio(audioUrl);
    
    currentAudio.addEventListener('timeupdate', () => {
      if (!currentAudio.duration) return;
      const percentage = (currentAudio.currentTime / currentAudio.duration) * 100;
      progressBar.style.width = `${percentage}%`;
      
      const currentMins = Math.floor(currentAudio.currentTime / 60);
      const currentSecs = Math.floor(currentAudio.currentTime % 60).toString().padStart(2, '0');
      timeDisplay[0].textContent = `${currentMins}:${currentSecs}`;
      
      const durationMins = Math.floor(currentAudio.duration / 60);
      const durationSecs = Math.floor(currentAudio.duration % 60).toString().padStart(2, '0');
      if (!isNaN(durationMins)) {
        timeDisplay[1].textContent = `${durationMins}:${durationSecs}`;
      }
    });

    currentAudio.addEventListener('ended', () => {
      isPlaying = false;
      playPauseBtn.innerHTML = '<i class="fa-solid fa-play"></i>';
      progressBar.style.width = '0%';
      timeDisplay[0].textContent = '0:00';
    });

    currentAudio.play().then(() => {
      isPlaying = true;
      playPauseBtn.innerHTML = '<i class="fa-solid fa-pause"></i>';
    }).catch(e => {
       console.error("Audio playback blocked", e);
       playPauseBtn.innerHTML = '<i class="fa-solid fa-play"></i>';
    });

    playPauseBtn.onclick = () => {
      if (isPlaying) {
        currentAudio.pause();
        isPlaying = false;
        playPauseBtn.innerHTML = '<i class="fa-solid fa-play"></i>';
      } else {
        currentAudio.play();
        isPlaying = true;
        playPauseBtn.innerHTML = '<i class="fa-solid fa-pause"></i>';
      }
    };
  } else {
    // Fake player if no audio url is provided
    playPauseBtn.innerHTML = '<i class="fa-solid fa-pause"></i>';
    progressBar.style.width = '100%';
    alert("This song does not have a streamable audio URL from the API.");
  }
}

/* =========================================================================
   FOUNDRY — 1v1 arena shooter
   Original code, original procedural assets (no external textures/models).
   ========================================================================= */

(function () {
'use strict';

// ---------------------------------------------------------------------
// Basic setup
// ---------------------------------------------------------------------
const canvas = document.getElementById('gameCanvas');
const renderer = new THREE.WebGLRenderer({ canvas, antialias: true, powerPreference: 'high-performance' });
renderer.setPixelRatio(Math.min(window.devicePixelRatio || 1, 2));
renderer.setSize(window.innerWidth, window.innerHeight);
renderer.shadowMap.enabled = true;
renderer.shadowMap.type = THREE.PCFSoftShadowMap;
renderer.outputColorSpace = THREE.SRGBColorSpace || THREE.sRGBEncoding;

const scene = new THREE.Scene();
scene.background = new THREE.Color(0x9fb8c8);
scene.fog = new THREE.Fog(0x9fb8c8, 35, 95);

const camera = new THREE.PerspectiveCamera(78, window.innerWidth / window.innerHeight, 0.05, 300);
const DEFAULT_FOV = 78;

window.addEventListener('resize', () => {
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(window.innerWidth, window.innerHeight);
});

// ---------------------------------------------------------------------
// Audio — everything synthesized with WebAudio (no sample files)
// ---------------------------------------------------------------------
const Audio_ = (function () {
  const Ctx = window.AudioContext || window.webkitAudioContext;
  const ctx = new Ctx();
  const master = ctx.createGain();
  master.gain.value = 0.85;
  master.connect(ctx.destination);

  function resume() { if (ctx.state === 'suspended') ctx.resume(); }

  function noiseBuffer(dur) {
    const n = Math.max(1, Math.floor(ctx.sampleRate * dur));
    const buf = ctx.createBuffer(1, n, ctx.sampleRate);
    const d = buf.getChannelData(0);
    for (let i = 0; i < n; i++) d[i] = Math.random() * 2 - 1;
    return buf;
  }

  function gunshot(kind, distGain) {
    resume();
    const now = ctx.currentTime;
    const g = ctx.createGain();
    g.gain.value = (distGain === undefined ? 1 : distGain) * 0.9;
    g.connect(master);

    // profile per weapon
    let dur, bp1, bp2, click;
    if (kind === 'pistol') { dur = 0.16; bp1 = 1800; bp2 = 320; click = 0.02; }
    else if (kind === 'rifle') { dur = 0.22; bp1 = 1300; bp2 = 200; click = 0.03; }
    else { dur = 0.5; bp1 = 700; bp2 = 90; click = 0.05; } // sniper - deeper, longer tail

    const src = ctx.createBufferSource();
    src.buffer = noiseBuffer(dur);
    const bp = ctx.createBiquadFilter();
    bp.type = 'bandpass';
    bp.frequency.setValueAtTime(bp1, now);
    bp.frequency.exponentialRampToValueAtTime(bp2, now + dur);
    bp.Q.value = 0.7;
    const env = ctx.createGain();
    env.gain.setValueAtTime(1.0, now);
    env.gain.exponentialRampToValueAtTime(0.001, now + dur);
    src.connect(bp); bp.connect(env); env.connect(g);
    src.start(now); src.stop(now + dur + 0.02);

    // low thump for body
    const osc = ctx.createOscillator();
    osc.type = 'sine';
    osc.frequency.setValueAtTime(kind === 'sniper' ? 110 : kind === 'rifle' ? 140 : 180, now);
    osc.frequency.exponentialRampToValueAtTime(40, now + click + 0.08);
    const oGain = ctx.createGain();
    oGain.gain.setValueAtTime(0.9, now);
    oGain.gain.exponentialRampToValueAtTime(0.001, now + click + 0.1);
    osc.connect(oGain); oGain.connect(g);
    osc.start(now); osc.stop(now + click + 0.12);
  }

  function footstep(gain) {
    resume();
    const now = ctx.currentTime;
    const g = ctx.createGain();
    g.gain.value = (gain === undefined ? 1 : gain) * 0.5;
    g.connect(master);
    const src = ctx.createBufferSource();
    src.buffer = noiseBuffer(0.08);
    const lp = ctx.createBiquadFilter();
    lp.type = 'lowpass';
    lp.frequency.value = 350 + Math.random() * 200;
    const env = ctx.createGain();
    env.gain.setValueAtTime(1, now);
    env.gain.exponentialRampToValueAtTime(0.001, now + 0.09);
    src.connect(lp); lp.connect(env); env.connect(g);
    src.start(now); src.stop(now + 0.1);
  }

  function hitmarker(headshot) {
    resume();
    const now = ctx.currentTime;
    const osc = ctx.createOscillator();
    osc.type = 'square';
    osc.frequency.setValueAtTime(headshot ? 1500 : 950, now);
    osc.frequency.exponentialRampToValueAtTime(headshot ? 2200 : 700, now + 0.06);
    const g = ctx.createGain();
    g.gain.setValueAtTime(0.35, now);
    g.gain.exponentialRampToValueAtTime(0.001, now + 0.09);
    osc.connect(g); g.connect(master);
    osc.start(now); osc.stop(now + 0.1);
  }

  function killStinger() {
    resume();
    const now = ctx.currentTime;
    [0, 0.09, 0.18].forEach((t, i) => {
      const osc = ctx.createOscillator();
      osc.type = 'triangle';
      osc.frequency.value = 440 * Math.pow(1.5, i);
      const g = ctx.createGain();
      g.gain.setValueAtTime(0.001, now + t);
      g.gain.linearRampToValueAtTime(0.3, now + t + 0.02);
      g.gain.exponentialRampToValueAtTime(0.001, now + t + 0.22);
      osc.connect(g); g.connect(master);
      osc.start(now + t); osc.stop(now + t + 0.25);
    });
  }

  function deathStinger() {
    resume();
    const now = ctx.currentTime;
    const osc = ctx.createOscillator();
    osc.type = 'sawtooth';
    osc.frequency.setValueAtTime(300, now);
    osc.frequency.exponentialRampToValueAtTime(50, now + 0.5);
    const g = ctx.createGain();
    g.gain.setValueAtTime(0.25, now);
    g.gain.exponentialRampToValueAtTime(0.001, now + 0.55);
    osc.connect(g); g.connect(master);
    osc.start(now); osc.stop(now + 0.6);
  }

  function respawn() {
    resume();
    const now = ctx.currentTime;
    const osc = ctx.createOscillator();
    osc.type = 'sine';
    osc.frequency.setValueAtTime(220, now);
    osc.frequency.linearRampToValueAtTime(660, now + 0.3);
    const g = ctx.createGain();
    g.gain.setValueAtTime(0.001, now);
    g.gain.linearRampToValueAtTime(0.25, now + 0.05);
    g.gain.exponentialRampToValueAtTime(0.001, now + 0.35);
    osc.connect(g); g.connect(master);
    osc.start(now); osc.stop(now + 0.4);
  }

  function reload(dur) {
    resume();
    const now = ctx.currentTime;
    [0, dur * 0.9].forEach((t) => {
      const osc = ctx.createOscillator();
      osc.type = 'square';
      osc.frequency.value = 500;
      const g = ctx.createGain();
      g.gain.setValueAtTime(0.001, now + t);
      g.gain.linearRampToValueAtTime(0.12, now + t + 0.01);
      g.gain.exponentialRampToValueAtTime(0.001, now + t + 0.06);
      osc.connect(g); g.connect(master);
      osc.start(now + t); osc.stop(now + t + 0.07);
    });
  }

  function emptyClick() {
    resume();
    const now = ctx.currentTime;
    const osc = ctx.createOscillator();
    osc.type = 'square';
    osc.frequency.value = 900;
    const g = ctx.createGain();
    g.gain.setValueAtTime(0.15, now);
    g.gain.exponentialRampToValueAtTime(0.001, now + 0.04);
    osc.connect(g); g.connect(master);
    osc.start(now); osc.stop(now + 0.05);
  }

  return { resume, gunshot, footstep, hitmarker, killStinger, deathStinger, respawn, reload, emptyClick };
})();
window.addEventListener('pointerdown', () => Audio_.resume(), { once: true });

// ---------------------------------------------------------------------
// Procedural textures (canvas-based) — original, no external images
// ---------------------------------------------------------------------
function makeCanvasTexture(draw, w, h, repeatX, repeatY) {
  const c = document.createElement('canvas');
  c.width = w; c.height = h;
  const ctx = c.getContext('2d');
  draw(ctx, w, h);
  const tex = new THREE.CanvasTexture(c);
  tex.wrapS = tex.wrapT = THREE.RepeatWrapping;
  tex.repeat.set(repeatX || 1, repeatY || 1);
  tex.anisotropy = 4;
  return tex;
}

const TEX = {};

TEX.floor = makeCanvasTexture((ctx, w, h) => {
  ctx.fillStyle = '#7c7669';
  ctx.fillRect(0, 0, w, h);
  for (let i = 0; i < 900; i++) {
    ctx.fillStyle = `rgba(0,0,0,${Math.random() * 0.06})`;
    ctx.fillRect(Math.random() * w, Math.random() * h, 2, 2);
  }
  ctx.strokeStyle = 'rgba(0,0,0,0.18)';
  ctx.lineWidth = 3;
  const cell = 64;
  for (let x = 0; x <= w; x += cell) { ctx.beginPath(); ctx.moveTo(x, 0); ctx.lineTo(x, h); ctx.stroke(); }
  for (let y = 0; y <= h; y += cell) { ctx.beginPath(); ctx.moveTo(0, y); ctx.lineTo(w, y); ctx.stroke(); }
}, 256, 256, 24, 16);

TEX.wall = makeCanvasTexture((ctx, w, h) => {
  ctx.fillStyle = '#b7ab93';
  ctx.fillRect(0, 0, w, h);
  ctx.strokeStyle = 'rgba(60,50,35,0.35)';
  ctx.lineWidth = 2;
  const bw = 64, bh = 28;
  for (let y = 0; y < h; y += bh) {
    const offset = (Math.floor(y / bh) % 2) * (bw / 2);
    for (let x = -bw; x < w + bw; x += bw) {
      ctx.strokeRect(x + offset, y, bw, bh);
    }
  }
  for (let i = 0; i < 400; i++) {
    ctx.fillStyle = `rgba(30,25,15,${Math.random() * 0.08})`;
    ctx.fillRect(Math.random() * w, Math.random() * h, 3, 3);
  }
}, 256, 128, 8, 3);

TEX.metal = makeCanvasTexture((ctx, w, h) => {
  ctx.fillStyle = '#5a6570';
  ctx.fillRect(0, 0, w, h);
  ctx.strokeStyle = 'rgba(0,0,0,0.3)';
  for (let x = 0; x < w; x += 16) { ctx.beginPath(); ctx.moveTo(x, 0); ctx.lineTo(x, h); ctx.stroke(); }
  ctx.fillStyle = 'rgba(255,255,255,0.06)';
  for (let i = 0; i < 60; i++) ctx.fillRect(Math.random() * w, Math.random() * h, 10, 1);
  ctx.strokeStyle = '#ff8a3d';
  ctx.lineWidth = 4;
  ctx.strokeRect(4, h - 10, w - 8, 4);
}, 128, 128, 4, 4);

TEX.crate = makeCanvasTexture((ctx, w, h) => {
  ctx.fillStyle = '#8a5a34';
  ctx.fillRect(0, 0, w, h);
  ctx.strokeStyle = 'rgba(40,20,5,0.5)';
  ctx.lineWidth = 4;
  for (let i = 0; i < 10; i++) {
    ctx.beginPath();
    ctx.moveTo(0, Math.random() * h);
    ctx.lineTo(w, Math.random() * h + (Math.random() * 10 - 5));
    ctx.stroke();
  }
  ctx.strokeStyle = 'rgba(20,10,0,0.7)';
  ctx.lineWidth = 6;
  ctx.strokeRect(3, 3, w - 6, h - 6);
  ctx.beginPath(); ctx.moveTo(3, 3); ctx.lineTo(w - 3, h - 3); ctx.stroke();
  ctx.beginPath(); ctx.moveTo(w - 3, 3); ctx.lineTo(3, h - 3); ctx.stroke();
}, 128, 128, 1, 1);

TEX.tower = makeCanvasTexture((ctx, w, h) => {
  ctx.fillStyle = '#8f9aa3';
  ctx.fillRect(0, 0, w, h);
  ctx.strokeStyle = 'rgba(0,0,0,0.25)';
  ctx.lineWidth = 2;
  const bw = 48, bh = 24;
  for (let y = 0; y < h; y += bh) for (let x = 0; x < w; x += bw) ctx.strokeRect(x, y, bw, bh);
  ctx.fillStyle = 'rgba(56,224,255,0.10)';
  ctx.fillRect(0, h - 14, w, 14);
}, 128, 128, 6, 3);

// ---------------------------------------------------------------------
// Lighting
// ---------------------------------------------------------------------
const hemi = new THREE.HemisphereLight(0xbfd9ea, 0x55483a, 0.75);
scene.add(hemi);

const sun = new THREE.DirectionalLight(0xfff2da, 1.35);
sun.position.set(-35, 55, 20);
sun.castShadow = true;
sun.shadow.mapSize.set(2048, 2048);
sun.shadow.camera.left = -45;
sun.shadow.camera.right = 45;
sun.shadow.camera.top = 40;
sun.shadow.camera.bottom = -40;
sun.shadow.camera.near = 1;
sun.shadow.camera.far = 130;
sun.shadow.bias = -0.0015;
scene.add(sun);
scene.add(sun.target);

const fillLight = new THREE.DirectionalLight(0xa9c8ff, 0.25);
fillLight.position.set(25, 20, -25);
scene.add(fillLight);

// ---------------------------------------------------------------------
// Map: "FOUNDRY" — mid lane w/ catwalk, two flank routes, crates, tower
// ---------------------------------------------------------------------
const WORLD = { colliders: [], platforms: [], ramps: [] };

function addCollider(x0, x1, z0, z1, y0, y1, mesh) {
  WORLD.colliders.push({ x0, x1, z0, z1, y0, y1, mesh });
}

function box(w, h, d, mat, x, y, z, castShadow) {
  const geo = new THREE.BoxGeometry(w, h, d);
  const m = new THREE.Mesh(geo, mat);
  m.position.set(x, y, z);
  m.castShadow = !!castShadow;
  m.receiveShadow = true;
  scene.add(m);
  return m;
}

function matFor(tex, color, rough) {
  return new THREE.MeshStandardMaterial({ map: tex, color: color || 0xffffff, roughness: rough === undefined ? 0.95 : rough, metalness: 0.05 });
}

const floorMat = matFor(TEX.floor, 0xffffff, 1.0);
const wallMat = matFor(TEX.wall, 0xffffff, 0.9);
const metalMat = matFor(TEX.metal, 0xffffff, 0.55);
const crateMat = matFor(TEX.crate, 0xffffff, 0.85);
const towerMat = matFor(TEX.tower, 0xffffff, 0.7);

// ground
const groundGeo = new THREE.PlaneGeometry(64, 40);
const ground = new THREE.Mesh(groundGeo, floorMat);
ground.rotation.x = -Math.PI / 2;
ground.receiveShadow = true;
scene.add(ground);

// perimeter walls
const ARENA_X = 30, ARENA_Z = 18, WALL_H = 6;
function perimeterWall(x0, x1, z0, z1) {
  const w = x1 - x0, d = z1 - z0;
  const m = box(w, WALL_H, d, wallMat, (x0 + x1) / 2, WALL_H / 2, (z0 + z1) / 2, true);
  addCollider(x0, x1, z0, z1, 0, WALL_H, m);
}
perimeterWall(-ARENA_X - 1, -ARENA_X, -ARENA_Z - 1, ARENA_Z + 1);
perimeterWall(ARENA_X, ARENA_X + 1, -ARENA_Z - 1, ARENA_Z + 1);
perimeterWall(-ARENA_X - 1, ARENA_X + 1, -ARENA_Z - 1, -ARENA_Z);
perimeterWall(-ARENA_X - 1, ARENA_X + 1, ARENA_Z, ARENA_Z + 1);

// mid/flank divider walls (with gaps as lane connectors)
function dividerWall(x0, x1, z0, z1) {
  const w = x1 - x0, d = z1 - z0;
  const m = box(w, WALL_H * 0.7, d, wallMat, (x0 + x1) / 2, (WALL_H * 0.7) / 2, (z0 + z1) / 2, true);
  addCollider(x0, x1, z0, z1, 0, WALL_H * 0.7, m);
}
// north divider (mid <-> north flank), gap near center for catwalk ramps
dividerWall(-28, -17, 2.85, 3.15);
dividerWall(17, 28, 2.85, 3.15);
// south divider (mid <-> south flank), wide central gap
dividerWall(-28, -10, -3.15, -2.85);
dividerWall(10, 28, -3.15, -2.85);

// crates for cover (scattered through mid + lanes)
const cratePositions = [
  [-6, 0.6, 1.2, 1.2], [6, 0.6, -1.4, 1.1], [-2, 0.6, -2.0, 1.0],
  [-18, 0.6, -12, 1.2], [18, 0.6, -12, 1.0], [-18, 0.6, 12, 1.1],
  [3, 0.6, 12.5, 1.0], [-24, 0.6, 4, 1.2], [22, 0.6, -6, 1.0],
];
cratePositions.forEach(([x, y, z, s]) => {
  const size = 1.3 * s;
  const m = box(size, size, size, crateMat, x, y, z, true);
  m.rotation.y = Math.random() * 0.6 - 0.3;
  addCollider(x - size / 2 - 0.08, x + size / 2 + 0.08, z - size / 2 - 0.08, z + size / 2 + 0.08, 0, size, m);
});
// stacked crate pair (small cover cluster with step-up feel, visual only — treated as one collider)
[[-12, -8], [12, 8]].forEach(([x, z]) => {
  box(1.3, 1.3, 1.3, crateMat, x, 0.65, z, true);
  box(1.3, 1.3, 1.3, crateMat, x + 0.9, 0.65, z + 0.2, true);
  addCollider(x - 0.75, x + 1.35, z - 0.75, z + 0.85, 0, 1.3, null);
});

// catwalk deck + ramps + rails (elevated route over mid lane, z 3.0-4.8)
const CATWALK_H = 3.2;
{
  const deck = box(28, 0.3, 1.8, metalMat, 0, CATWALK_H, 3.9, true);
  deck.geometry = new THREE.BoxGeometry(28, 0.3, 1.8);
  addCollider(-14, 14, 3.0, 4.8, 0, CATWALK_H, null); // whole footprint solid to bullets/collision below deck top (handled via height fn for walking)
  // guard rails
  const railMat = metalMat;
  box(28, 0.9, 0.08, railMat, 0, CATWALK_H + 0.75, 4.78, true);
  box(28, 0.9, 0.08, railMat, 0, CATWALK_H + 0.75, 3.02, true);
  // ramps (visual)
  const rampGeoW = new THREE.BoxGeometry(3.2, 0.3, 1.8);
  const rampW = new THREE.Mesh(rampGeoW, metalMat);
  rampW.position.set(-15.2, CATWALK_H / 2, 3.9);
  rampW.rotation.z = Math.atan2(CATWALK_H, 3.2);
  rampW.castShadow = true; rampW.receiveShadow = true;
  scene.add(rampW);
  const rampE = rampW.clone();
  rampE.position.set(15.2, CATWALK_H / 2, 3.9);
  rampE.rotation.z = -Math.atan2(CATWALK_H, 3.2);
  scene.add(rampE);
}
WORLD.platforms.push({ x0: -14, x1: 14, z0: 3.0, z1: 4.8, h: CATWALK_H });
WORLD.ramps.push({ x0: -16.6, x1: -14, z0: 3.0, z1: 4.8, h0: 0, h1: CATWALK_H, axis: 'x' });
WORLD.ramps.push({ x0: 14, x1: 16.6, z0: 3.0, z1: 4.8, h0: CATWALK_H, h1: 0, axis: 'x' });

// tower (rooftop sniper perch on the NE flank, reached by external ramp)
const TOWER_H = 3.6;
{
  const roof = box(6, 0.3, 6, towerMat, 22, TOWER_H, 12, true);
  const legMat = towerMat;
  [[19.3, 9.3], [24.7, 9.3], [19.3, 14.7], [24.7, 14.7]].forEach(([x, z]) => {
    box(0.5, TOWER_H, 0.5, legMat, x, TOWER_H / 2, z, true);
  });
  box(6.2, 0.8, 0.1, metalMat, 22, TOWER_H + 0.65, 9, true);
  box(6.2, 0.8, 0.1, metalMat, 22, TOWER_H + 0.65, 15, true);
  const rampGeo = new THREE.BoxGeometry(6, 0.3, 5.6);
  const ramp = new THREE.Mesh(rampGeo, towerMat);
  ramp.position.set(22, TOWER_H / 2, 17.8);
  ramp.rotation.x = -Math.atan2(TOWER_H, 5.6);
  ramp.castShadow = true; ramp.receiveShadow = true;
  scene.add(ramp);
}
WORLD.platforms.push({ x0: 19, x1: 25, z0: 9, z1: 15, h: TOWER_H });
WORLD.ramps.push({ x0: 19, x1: 25, z0: 15, z1: 20.6, h0: TOWER_H, h1: 0, axis: 'z' });

// low walls inside flank tunnels for extra cover / readability
[[-22, -14, 1.6], [22, 14, -1.6], [-8, 16.5, 1.4], [8, -16.5, -1.4]].forEach(([x, z, rot]) => {
  const m = box(3.2, 1.1, 0.4, wallMat, x, 0.55, z, true);
  m.rotation.y = rot;
  addCollider(x - 1.7, x + 1.7, z - 0.6, z + 0.6, 0, 1.1, m);
});

// sky gradient dome (simple, cheap, original)
{
  const skyGeo = new THREE.SphereGeometry(140, 16, 12);
  const skyMat = new THREE.ShaderMaterial({
    side: THREE.BackSide,
    uniforms: { top: { value: new THREE.Color(0x8fb8d6) }, bottom: { value: new THREE.Color(0xe9e2c8) } },
    vertexShader: `varying vec3 vPos; void main(){ vPos = position; gl_Position = projectionMatrix * modelViewMatrix * vec4(position,1.0); }`,
    fragmentShader: `varying vec3 vPos; uniform vec3 top; uniform vec3 bottom; void main(){ float h = normalize(vPos).y * 0.5 + 0.5; gl_FragColor = vec4(mix(bottom, top, h), 1.0); }`
  });
  scene.add(new THREE.Mesh(skyGeo, skyMat));
}

function getGroundHeight(x, z) {
  for (const r of WORLD.ramps) {
    if (x >= r.x0 && x <= r.x1 && z >= r.z0 && z <= r.z1) {
      const t = r.axis === 'x' ? (x - r.x0) / (r.x1 - r.x0) : (z - r.z0) / (r.z1 - r.z0);
      return r.h0 + (r.h1 - r.h0) * Math.min(1, Math.max(0, t));
    }
  }
  for (const p of WORLD.platforms) {
    if (x >= p.x0 && x <= p.x1 && z >= p.z0 && z <= p.z1) return p.h;
  }
  return 0;
}

function resolveXZCollision(pos, radius, prevY) {
  for (const c of WORLD.colliders) {
    // only collide if vertical ranges overlap (lets you walk under/over some things if needed)
    if (prevY + 1.6 < c.y0 || prevY > c.y1 + 0.05) continue;
    const cx = Math.max(c.x0, Math.min(pos.x, c.x1));
    const cz = Math.max(c.z0, Math.min(pos.z, c.z1));
    const dx = pos.x - cx, dz = pos.z - cz;
    const distSq = dx * dx + dz * dz;
    if (distSq < radius * radius && distSq > 1e-6) {
      const dist = Math.sqrt(distSq);
      const push = (radius - dist) / dist;
      pos.x += dx * push;
      pos.z += dz * push;
    } else if (distSq <= 1e-6) {
      pos.x += radius; // degenerate, nudge out
    }
  }
}

// ---------------------------------------------------------------------
// Spawn points & waypoint graph
// ---------------------------------------------------------------------
const SPAWNS = [
  { x: -26, z: 0 }, { x: 26, z: 0 },
  { x: 0, z: -15 }, { x: 0, z: 15 },
  { x: -24, z: -14 }, { x: 24, z: 14 },
  { x: 22, z: 12, tower: true },
  { x: -6, z: 1 }, { x: 6, z: -1 },
];

function spawnWorldPos(sp) {
  const y = sp.tower ? TOWER_H : getGroundHeight(sp.x, sp.z);
  return new THREE.Vector3(sp.x, y, sp.z);
}

const WP = {
  n1: { x: -26, z: 0 }, n2: { x: -10, z: 0 }, n3: { x: 0, z: 0 }, n4: { x: 10, z: 0 }, n5: { x: 26, z: 0 },
  n6: { x: -12, z: 4, y: CATWALK_H }, n7: { x: 12, z: 4, y: CATWALK_H },
  n8: { x: -20, z: -14 }, n9: { x: 0, z: -14 }, n10: { x: 20, z: -14 },
  n11: { x: -20, z: 14 }, n12: { x: 0, z: 14 }, n13: { x: 20, z: 14 },
  n14: { x: 22, z: 17.5 }, n15: { x: 22, z: 12, y: TOWER_H },
  n16: { x: -6, z: 1 }, n17: { x: 6, z: -1 },
};
const WP_EDGES = [
  ['n1', 'n2'], ['n2', 'n3'], ['n3', 'n4'], ['n4', 'n5'],
  ['n2', 'n8'], ['n2', 'n11'], ['n4', 'n10'], ['n4', 'n13'],
  ['n8', 'n9'], ['n9', 'n10'], ['n11', 'n12'], ['n12', 'n13'],
  ['n2', 'n6'], ['n4', 'n7'], ['n6', 'n7'], ['n6', 'n11'], ['n7', 'n13'],
  ['n13', 'n14'], ['n14', 'n15'],
  ['n3', 'n16'], ['n16', 'n2'], ['n16', 'n8'], ['n16', 'n11'],
  ['n3', 'n17'], ['n17', 'n4'], ['n17', 'n9'], ['n17', 'n13'],
];
const WP_ADJ = {};
Object.keys(WP).forEach((k) => (WP_ADJ[k] = []));
WP_EDGES.forEach(([a, b]) => { WP_ADJ[a].push(b); WP_ADJ[b].push(a); });

function wpPos(id) {
  const n = WP[id];
  return new THREE.Vector3(n.x, n.y !== undefined ? n.y : getGroundHeight(n.x, n.z), n.z);
}
function nearestNode(pos) {
  let best = null, bestD = Infinity;
  for (const k of Object.keys(WP)) {
    const d = wpPos(k).distanceTo(pos);
    if (d < bestD) { bestD = d; best = k; }
  }
  return best;
}
function bfsPath(fromId, toId) {
  if (fromId === toId) return [fromId];
  const visited = { [fromId]: true };
  const queue = [[fromId]];
  while (queue.length) {
    const path = queue.shift();
    const last = path[path.length - 1];
    for (const nb of WP_ADJ[last]) {
      if (visited[nb]) continue;
      visited[nb] = true;
      const newPath = path.concat([nb]);
      if (nb === toId) return newPath;
      queue.push(newPath);
    }
  }
  return [fromId];
}

// ---------------------------------------------------------------------
// Weapons
// ---------------------------------------------------------------------
const WEAPONS = {
  pistol: {
    id: 'pistol', name: 'PEACEKEEPER', slot: 1, automatic: false,
    damageNear: 34, damageFar: 20, rangeNear: 10, rangeFar: 38,
    headshotMult: 2.2, fireInterval: 0.19, magSize: 12, reserveSize: 36, reloadTime: 1.05,
    spreadStand: 0.006, spreadMove: 0.028, spreadCrouch: 0.003,
    recoil: [[0, 0.9], [0.15, 1.0], [-0.1, 1.05], [0.1, 0.95], [-0.15, 1.0], [0.05, 1.0]],
    recoilRecover: 9.0, viewKick: 0.012, adsFov: 1.0,
  },
  rifle: {
    id: 'rifle', name: 'VANTAGE-7', slot: 2, automatic: true,
    damageNear: 27, damageFar: 16, rangeNear: 14, rangeFar: 50,
    headshotMult: 2.0, fireInterval: 0.098, magSize: 30, reserveSize: 90, reloadTime: 1.9,
    spreadStand: 0.009, spreadMove: 0.05, spreadCrouch: 0.005,
    recoil: [[0, 0.5], [0.05, 0.7], [-0.08, 0.9], [0.12, 1.1], [-0.14, 1.2], [0.16, 1.3],
      [-0.1, 1.35], [0.08, 1.4], [-0.05, 1.4], [0, 1.4]],
    recoilRecover: 6.0, viewKick: 0.02, adsFov: 1.0,
  },
  sniper: {
    id: 'sniper', name: 'LONGCUT', slot: 3, automatic: false,
    damageNear: 100, damageFar: 62, rangeNear: 18, rangeFar: 70,
    headshotMult: 2.6, fireInterval: 1.25, magSize: 5, reserveSize: 15, reloadTime: 2.6,
    spreadStand: 0.0015, spreadMove: 0.09, spreadCrouch: 0.0006,
    recoil: [[0, 3.2], [0, 3.2], [0, 3.2], [0, 3.2], [0, 3.2]],
    recoilRecover: 3.0, viewKick: 0.05, adsFov: 0.42,
  },
};
const WEAPON_ORDER = ['pistol', 'rifle', 'sniper'];

function damageAtRange(w, dist) {
  if (dist <= w.rangeNear) return w.damageNear;
  if (dist >= w.rangeFar) return w.damageFar;
  const t = (dist - w.rangeNear) / (w.rangeFar - w.rangeNear);
  return w.damageNear + (w.damageFar - w.damageNear) * t;
}

// ---------------------------------------------------------------------
// Muzzle flash / impact / hit VFX
// ---------------------------------------------------------------------
const flashMat = new THREE.SpriteMaterial({ color: 0xffdca0, transparent: true, opacity: 0.95, depthWrite: false, blending: THREE.AdditiveBlending });
function spawnMuzzleFlash(position, quaternion) {
  const spr = new THREE.Sprite(flashMat.clone());
  spr.scale.set(0.5, 0.5, 0.5);
  spr.position.copy(position);
  scene.add(spr);
  const start = performance.now();
  (function anim() {
    const t = (performance.now() - start) / 60;
    if (t >= 1) { scene.remove(spr); return; }
    spr.material.opacity = 0.95 * (1 - t);
    spr.scale.setScalar(0.5 * (1 + t * 0.6));
    requestAnimationFrame(anim);
  })();
}

const impactPool = [];
function spawnImpact(position, normal, colorHex) {
  const geo = new THREE.BufferGeometry();
  const n = 10;
  const positions = new Float32Array(n * 3);
  const velocities = [];
  for (let i = 0; i < n; i++) {
    positions[i * 3] = position.x; positions[i * 3 + 1] = position.y; positions[i * 3 + 2] = position.z;
    const dir = normal.clone().add(new THREE.Vector3((Math.random() - 0.5), (Math.random() - 0.5), (Math.random() - 0.5)).multiplyScalar(0.8)).normalize();
    velocities.push(dir.multiplyScalar(1.5 + Math.random() * 2.5));
  }
  geo.setAttribute('position', new THREE.BufferAttribute(positions, 3));
  const mat = new THREE.PointsMaterial({ color: colorHex || 0xffd27a, size: 0.06, transparent: true, opacity: 1 });
  const pts = new THREE.Points(geo, mat);
  scene.add(pts);
  const start = performance.now();
  const decal = new THREE.Mesh(new THREE.CircleGeometry(0.06, 8), new THREE.MeshBasicMaterial({ color: 0x1a1a1a, transparent: true, opacity: 0.6 }));
  decal.position.copy(position).add(normal.clone().multiplyScalar(0.01));
  decal.lookAt(position.clone().add(normal));
  scene.add(decal);
  setTimeout(() => scene.remove(decal), 6000);
  (function anim() {
    const dt = 1 / 60;
    const t = (performance.now() - start) / 400;
    if (t >= 1) { scene.remove(pts); return; }
    const arr = geo.attributes.position.array;
    for (let i = 0; i < n; i++) {
      arr[i * 3] += velocities[i].x * dt;
      arr[i * 3 + 1] += (velocities[i].y - 4 * t) * dt;
      arr[i * 3 + 2] += velocities[i].z * dt;
    }
    geo.attributes.position.needsUpdate = true;
    mat.opacity = 1 - t;
    requestAnimationFrame(anim);
  })();
}

// ---------------------------------------------------------------------
// HUD helpers
// ---------------------------------------------------------------------
const HUD = {
  youScore: document.getElementById('youScore'),
  botScore: document.getElementById('botScore'),
  killfeed: document.getElementById('killfeed'),
  healthFg: document.getElementById('healthBarFg'),
  healthNum: document.getElementById('healthNum'),
  ammoInMag: document.getElementById('ammoInMag'),
  ammoReserve: document.getElementById('ammoReserve'),
  weaponName: document.getElementById('weaponName'),
  reloadBar: document.getElementById('reloadBar'),
  reloadBarFg: document.getElementById('reloadBarFg'),
  crosshair: document.getElementById('crosshair'),
  hitmarker: document.getElementById('hitmarker'),
  vignette: document.getElementById('dmgVignette'),
  slots: Array.from(document.querySelectorAll('.wslot')),
  hearIndicator: document.getElementById('hearIndicator'),
  startOverlay: document.getElementById('startOverlay'),
  winOverlay: document.getElementById('winOverlay'),
  winTitle: document.getElementById('winTitle'),
  finalScore: document.getElementById('finalScore'),
  playBtn: document.getElementById('playBtn'),
  rematchBtn: document.getElementById('rematchBtn'),
  diffRow: document.getElementById('diffRow'),
  lockHint: document.getElementById('lockHint'),
};

function setSpread(px) { HUD.crosshair.style.setProperty('--spread', px + 'px'); }
function flashHit(kill, headshot) {
  Audio_.hitmarker(headshot);
  HUD.hitmarker.classList.remove('show', 'kill');
  void HUD.hitmarker.offsetWidth;
  if (kill) HUD.hitmarker.classList.add('kill');
  HUD.hitmarker.classList.add('show');
}
function flashDamage() {
  HUD.vignette.classList.add('hit');
  setTimeout(() => HUD.vignette.classList.remove('hit'), 220);
}
function pushKillFeed(killerIsYou, victimIsYou, weaponName, headshot) {
  const row = document.createElement('div');
  row.className = 'kf-row';
  const killerLabel = killerIsYou ? '<span class="kfyou">YOU</span>' : '<span class="kfbot">BOT</span>';
  const victimLabel = victimIsYou ? '<span class="kfyou">YOU</span>' : '<span class="kfbot">BOT</span>';
  row.innerHTML = `${killerLabel} <span class="kfweap">[${weaponName}${headshot ? ' • HS' : ''}]</span> ${victimLabel}`;
  if (headshot) row.querySelector('.kfweap').classList.add('kfhs');
  HUD.killfeed.appendChild(row);
  setTimeout(() => row.remove(), 4200);
}

// ---------------------------------------------------------------------
// Game state
// ---------------------------------------------------------------------
const GAME = {
  running: false,
  paused: true,
  difficulty: 'normal',
  scoreYou: 0,
  scoreBot: 0,
  winScore: 20,
  clock: new THREE.Clock(),
};

const KEYS = {};
let mouseDX = 0, mouseDY = 0;
let mouseDown = false;
let rightDown = false;

document.addEventListener('keydown', (e) => {
  KEYS[e.code] = true;
  if (e.code === 'Digit1') player.switchWeapon('pistol');
  if (e.code === 'Digit2') player.switchWeapon('rifle');
  if (e.code === 'Digit3') player.switchWeapon('sniper');
  if (e.code === 'KeyR') player.reload();
  if (e.code === 'Escape') { document.exitPointerLock && document.exitPointerLock(); }
});
document.addEventListener('keyup', (e) => { KEYS[e.code] = false; });
canvas.addEventListener('mousedown', (e) => { if (e.button === 0) mouseDown = true; if (e.button === 2) rightDown = true; });
window.addEventListener('mouseup', (e) => { if (e.button === 0) mouseDown = false; if (e.button === 2) rightDown = false; });
canvas.addEventListener('contextmenu', (e) => e.preventDefault());

document.addEventListener('mousemove', (e) => {
  if (document.pointerLockElement === canvas) {
    mouseDX += e.movementX || 0;
    mouseDY += e.movementY || 0;
  }
});
document.addEventListener('pointerlockchange', () => {
  const locked = document.pointerLockElement === canvas;
  HUD.lockHint.style.display = (!locked && GAME.running) ? 'flex' : 'none';
  GAME.paused = !locked;
});
canvas.addEventListener('click', () => {
  if (GAME.running) canvas.requestPointerLock();
});

// ---------------------------------------------------------------------
// Player
// ---------------------------------------------------------------------
const player = {
  pos: new THREE.Vector3(-26, 0, 0),
  vy: 0,
  grounded: true,
  yaw: 0,
  pitch: 0,
  height: 1.7,
  crouchHeight: 1.15,
  radius: 0.38,
  speed: 5.2,
  hp: 100,
  alive: true,
  weaponId: 'pistol',
  ammo: {},
  reserve: {},
  reloading: false,
  reloadT: 0,
  fireCooldown: 0,
  recoilShotIndex: 0,
  recoilPitch: 0,
  recoilYaw: 0,
  lastFireTime: -10,
  footstepTimer: 0,
  moveSpeedNorm: 0, // 0..1 for spread calc
  crouching: false,

  init() {
    WEAPON_ORDER.forEach((id) => { this.ammo[id] = WEAPONS[id].magSize; this.reserve[id] = WEAPONS[id].reserveSize; });
  },

  switchWeapon(id) {
    if (this.weaponId === id || this.reloading) return;
    this.weaponId = id;
    this.recoilShotIndex = 0;
    HUD.slots.forEach((s) => s.classList.toggle('active', s.dataset.slot === String(WEAPONS[id].slot)));
    HUD.weaponName.textContent = WEAPONS[id].name;
    updateAmmoHud();
  },

  currentWeapon() { return WEAPONS[this.weaponId]; },

  reload() {
    const w = this.currentWeapon();
    if (this.reloading || this.ammo[w.id] >= w.magSize || this.reserve[w.id] <= 0) return;
    this.reloading = true;
    this.reloadT = 0;
    HUD.reloadBar.style.display = 'block';
    Audio_.reload(w.reloadTime);
  },

  respawn(spawnPos) {
    this.pos.copy(spawnPos);
    this.vy = 0;
    this.hp = 100;
    this.alive = true;
    this.reloading = false;
    HUD.reloadBar.style.display = 'none';
    WEAPON_ORDER.forEach((id) => { this.ammo[id] = WEAPONS[id].magSize; this.reserve[id] = WEAPONS[id].reserveSize; });
    updateAmmoHud();
    updateHealthHud();
    Audio_.respawn();
  },

  takeDamage(dmg, headshot) {
    if (!this.alive) return;
    this.hp -= dmg;
    flashDamage();
    updateHealthHud();
    if (this.hp <= 0) this.die();
  },

  die() {
    this.alive = false;
    Audio_.deathStinger();
    GAME.scoreBot++;
    HUD.botScore.textContent = 'BOT ' + GAME.scoreBot;
    pushKillFeed(false, true, bot.currentWeaponName(), bot.lastShotHeadshot);
    checkWin();
    if (GAME.running) {
      setTimeout(() => {
        if (!GAME.running) return;
        const sp = pickRespawn(bot.pos);
        this.respawn(sp);
      }, 2000);
    }
  },
};
player.init();

function pickRespawn(awayFromPos) {
  let best = null, bestD = -1;
  const candidates = SPAWNS.slice();
  for (let i = candidates.length - 1; i > 0; i--) { const j = Math.floor(Math.random() * (i + 1));[candidates[i], candidates[j]] = [candidates[j], candidates[i]]; }
  for (const sp of candidates) {
    const wp = spawnWorldPos(sp);
    const d = awayFromPos ? wp.distanceTo(awayFromPos) : 999;
    if (d > bestD) { bestD = d; best = wp; }
    if (d > 18) return wp; // good enough, far away
  }
  return best || spawnWorldPos(SPAWNS[0]);
}

function updateAmmoHud() {
  const w = player.currentWeapon();
  HUD.ammoInMag.textContent = player.ammo[w.id];
  HUD.ammoReserve.textContent = player.reserve[w.id];
}
function updateHealthHud() {
  const hp = Math.max(0, player.hp);
  HUD.healthFg.style.width = hp + '%';
  HUD.healthNum.textContent = Math.round(hp);
  HUD.healthFg.style.background = hp > 50 ? 'linear-gradient(90deg,#39c26a,#8ee06f)' : hp > 25 ? 'linear-gradient(90deg,#e0b23c,#f2d06a)' : 'linear-gradient(90deg,#d43c3c,#ff7a7a)';
}
function checkWin() {
  if (GAME.scoreYou >= GAME.winScore || GAME.scoreBot >= GAME.winScore) {
    GAME.running = false;
    document.exitPointerLock && document.exitPointerLock();
    const youWin = GAME.scoreYou >= GAME.winScore;
    HUD.winTitle.textContent = youWin ? 'VICTORY' : 'DEFEATED';
    HUD.winTitle.className = youWin ? 'win' : 'lose';
    HUD.finalScore.textContent = `${GAME.scoreYou} — ${GAME.scoreBot}`;
    HUD.winOverlay.classList.remove('hidden');
  }
}

// ---------------------------------------------------------------------
// Camera rig (weapon view model - simple procedural low-poly gun)
// ---------------------------------------------------------------------
const viewGunGroup = new THREE.Group();
camera.add(viewGunGroup);
scene.add(camera);

function buildViewGun(weaponId) {
  while (viewGunGroup.children.length) viewGunGroup.remove(viewGunGroup.children[0]);
  const bodyMat = new THREE.MeshStandardMaterial({ color: 0x2b2f33, roughness: 0.4, metalness: 0.6 });
  const accentMat = new THREE.MeshStandardMaterial({ color: 0xff8a3d, roughness: 0.4, metalness: 0.3 });
  const g = new THREE.Group();
  if (weaponId === 'pistol') {
    g.add(meshAt(new THREE.BoxGeometry(0.07, 0.12, 0.28), bodyMat, 0, -0.02, 0));
    g.add(meshAt(new THREE.BoxGeometry(0.05, 0.14, 0.06), bodyMat, 0, -0.12, 0.08));
    g.position.set(0.16, -0.16, -0.35);
  } else if (weaponId === 'rifle') {
    g.add(meshAt(new THREE.BoxGeometry(0.08, 0.1, 0.62), bodyMat, 0, -0.02, -0.05));
    g.add(meshAt(new THREE.BoxGeometry(0.05, 0.16, 0.08), bodyMat, 0, -0.12, 0.12));
    g.add(meshAt(new THREE.BoxGeometry(0.03, 0.03, 0.22), accentMat, 0, 0.03, -0.5));
    g.position.set(0.18, -0.18, -0.35);
  } else {
    g.add(meshAt(new THREE.BoxGeometry(0.07, 0.09, 0.85), bodyMat, 0, -0.01, -0.1));
    g.add(meshAt(new THREE.BoxGeometry(0.05, 0.15, 0.07), bodyMat, 0, -0.12, 0.18));
    g.add(meshAt(new THREE.BoxGeometry(0.09, 0.09, 0.16), accentMat, 0, 0.08, -0.35));
    g.position.set(0.17, -0.2, -0.4);
  }
  viewGunGroup.add(g);
  return g;
}
function meshAt(geo, mat, x, y, z) {
  const m = new THREE.Mesh(geo, mat);
  m.position.set(x, y, z);
  return m;
}
let currentViewGun = buildViewGun('pistol');
let muzzleWorldPos = new THREE.Vector3();

// ---------------------------------------------------------------------
// Bot
// ---------------------------------------------------------------------
const DIFFICULTY = {
  easy: { reaction: 0.5, aimError: 0.09, fireMult: 1.6, hearing: 16, holdChance: 0.15, hp: 90, dmgMult: 0.8 },
  normal: { reaction: 0.28, aimError: 0.045, fireMult: 1.15, hearing: 24, holdChance: 0.35, hp: 100, dmgMult: 1.0 },
  hard: { reaction: 0.12, aimError: 0.018, fireMult: 0.92, hearing: 34, holdChance: 0.55, hp: 100, dmgMult: 1.15 },
};

const bot = {
  pos: new THREE.Vector3(26, 0, 0),
  vy: 0,
  yaw: Math.PI,
  hp: 100,
  alive: true,
  speed: 4.4,
  radius: 0.4,
  height: 1.7,
  state: 'PATROL', // PATROL, INVESTIGATE, CHASE, HOLD
  path: [],
  pathTarget: null,
  lastHeardPos: null,
  lastHeardTime: -99,
  holdUntil: 0,
  strafeDir: 1,
  strafeTimer: 0,
  fireCooldown: 0,
  reactionTimer: 0,
  hitFlinch: 0,
  lastShotHeadshot: false,
  mesh: null, head: null, body: null,

  build() {
    const group = new THREE.Group();
    const bodyMat = new THREE.MeshStandardMaterial({ color: 0xc4442e, roughness: 0.55, metalness: 0.1 });
    const headMat = new THREE.MeshStandardMaterial({ color: 0xe0a479, roughness: 0.6 });
    const torso = new THREE.Mesh(new THREE.CapsuleGeometry(0.32, 0.75, 4, 8), bodyMat);
    torso.position.y = 0.95;
    torso.castShadow = true; torso.receiveShadow = true;
    torso.userData.isBody = true;
    group.add(torso);
    const head = new THREE.Mesh(new THREE.SphereGeometry(0.22, 12, 10), headMat);
    head.position.y = 1.55;
    head.castShadow = true;
    head.userData.isHead = true;
    group.add(head);
    // shoulder accent stripes for readable silhouette
    const stripeMat = new THREE.MeshStandardMaterial({ color: 0xffd23f, roughness: 0.5 });
    const stripe = new THREE.Mesh(new THREE.BoxGeometry(0.66, 0.1, 0.36), stripeMat);
    stripe.position.y = 1.25;
    group.add(stripe);
    const gunMat = new THREE.MeshStandardMaterial({ color: 0x22262a, roughness: 0.4, metalness: 0.6 });
    const gun = new THREE.Mesh(new THREE.BoxGeometry(0.08, 0.1, 0.5), gunMat);
    gun.position.set(0.28, 1.0, 0.25);
    group.add(gun);
    this.gunMesh = gun;
    scene.add(group);
    this.mesh = group; this.head = head; this.body = torso;
  },

  currentWeaponName() { return 'VANTAGE-7'; },

  applyDifficulty(diff) {
    const d = DIFFICULTY[diff];
    this.maxHp = d.hp;
    this.hp = d.hp;
    this.diffParams = d;
  },

  respawn(pos) {
    this.pos.copy(pos);
    this.vy = 0;
    this.hp = this.maxHp;
    this.alive = true;
    this.state = 'PATROL';
    this.path = [];
    this.mesh.visible = true;
    Audio_.respawn();
  },

  takeDamage(dmg, headshot, attackerPos) {
    if (!this.alive) return;
    this.hp -= dmg;
    this.hitFlinch = 0.25;
    this.lastHeardPos = attackerPos.clone();
    this.lastHeardTime = GAME.clock.elapsedTime;
    if (this.state === 'PATROL') this.state = 'INVESTIGATE';
    if (this.hp <= 0) this.die();
  },

  die() {
    this.alive = false;
    this.mesh.visible = false;
    Audio_.killStinger();
    GAME.scoreYou++;
    HUD.youScore.textContent = 'YOU ' + GAME.scoreYou;
    pushKillFeed(true, false, player.currentWeapon().name, lastPlayerShotHeadshot);
    checkWin();
    if (GAME.running) {
      setTimeout(() => {
        if (!GAME.running) return;
        const sp = pickRespawn(player.pos);
        this.respawn(sp);
      }, 2000);
    }
  },

  eyePos() { return new THREE.Vector3(this.pos.x, this.pos.y + 1.55, this.pos.z); },

  hasLineOfSight(targetPos) {
    const from = this.eyePos();
    const dir = targetPos.clone().sub(from);
    const dist = dir.length();
    dir.normalize();
    for (const c of WORLD.colliders) {
      if (!c.mesh) continue;
      const box3 = new THREE.Box3(new THREE.Vector3(c.x0, c.y0, c.z0), new THREE.Vector3(c.x1, c.y1, c.z1));
      const ray = new THREE.Ray(from, dir);
      const hit = new THREE.Vector3();
      if (ray.intersectBox(box3, hit)) {
        if (from.distanceTo(hit) < dist - 0.3) return false;
      }
    }
    return true;
  },
};
bot.build();

let lastPlayerShotHeadshot = false;

// ---------------------------------------------------------------------
// Shooting logic (shared raycast approach)
// ---------------------------------------------------------------------
const raycaster = new THREE.Raycaster();

function playerTryFire(dt) {
  const w = player.currentWeapon();
  if (!player.alive || player.reloading) return;
  if (player.fireCooldown > 0) return;
  if (player.ammo[w.id] <= 0) {
    if (mouseDown) Audio_.emptyClick();
    player.fireCooldown = 0.25;
    return;
  }
  if (!mouseDown) return;
  if (!w.automatic && player.firedThisClick) return;

  player.ammo[w.id]--;
  player.fireCooldown = w.fireInterval;
  player.firedThisClick = true;
  updateAmmoHud();

  // recoil kick
  const kick = w.recoil[player.recoilShotIndex % w.recoil.length];
  player.recoilYaw += kick[0] * w.viewKick;
  player.recoilPitch += kick[1] * w.viewKick;
  player.recoilShotIndex++;

  // spread based on movement
  const spread = player.crouching ? w.spreadCrouch : (player.moveSpeedNorm > 0.05 ? THREE.MathUtils.lerp(w.spreadStand, w.spreadMove, Math.min(1, player.moveSpeedNorm)) : w.spreadStand);
  const dir = new THREE.Vector3();
  camera.getWorldDirection(dir);
  dir.x += (Math.random() - 0.5) * spread * 2;
  dir.y += (Math.random() - 0.5) * spread * 2;
  dir.z += (Math.random() - 0.5) * spread * 2;
  dir.normalize();

  const origin = camera.getWorldPosition(new THREE.Vector3());
  raycaster.set(origin, dir);
  raycaster.far = 120;

  Audio_.gunshot(w.id, 1);
  currentViewGun.getWorldPosition(muzzleWorldPos);
  spawnMuzzleFlash(muzzleWorldPos, camera.quaternion);

  // hit test vs bot
  let hitSomething = false;
  if (bot.alive) {
    const targets = [bot.head, bot.body];
    const hits = raycaster.intersectObjects(targets, false);
    if (hits.length) {
      const hit = hits[0];
      const headshot = !!hit.object.userData.isHead;
      const dist = origin.distanceTo(hit.point);
      let dmg = damageAtRange(w, dist);
      if (headshot) dmg *= w.headshotMult;
      lastPlayerShotHeadshot = headshot;
      const wasAlive = bot.alive;
      bot.takeDamage(dmg, headshot, origin.clone());
      flashHit(!bot.alive && wasAlive, headshot);
      spawnImpact(hit.point, hit.face ? hit.face.normal.clone().transformDirection(hit.object.matrixWorld) : dir.clone().negate(), 0xff5a4a);
      hitSomething = true;
    }
  }
  if (!hitSomething) {
    // hit world geometry -> impact fx
    const worldHits = raycaster.intersectObjects(scene.children.filter((o) => o.isMesh && o !== bot.head && o !== bot.body), false);
    if (worldHits.length) {
      const h = worldHits[0];
      const n = h.face ? h.face.normal.clone().transformDirection(h.object.matrixWorld) : new THREE.Vector3(0, 1, 0);
      spawnImpact(h.point, n, 0xffe6b0);
    }
  }
}

function tryReloadFinish(dt) {
  if (!player.reloading) return;
  const w = player.currentWeapon();
  player.reloadT += dt;
  HUD.reloadBarFg.style.width = Math.min(100, (player.reloadT / w.reloadTime) * 100) + '%';
  if (player.reloadT >= w.reloadTime) {
    const need = w.magSize - player.ammo[w.id];
    const take = Math.min(need, player.reserve[w.id]);
    player.ammo[w.id] += take;
    player.reserve[w.id] -= take;
    player.reloading = false;
    HUD.reloadBar.style.display = 'none';
    updateAmmoHud();
  }
}

// ---------------------------------------------------------------------
// Bot AI update
// ---------------------------------------------------------------------
function botFire(dt, dist, targetPos) {
  const d = bot.diffParams;
  bot.fireCooldown -= dt;
  if (bot.fireCooldown > 0) return;
  const baseInterval = 0.14 * d.fireMult;
  bot.fireCooldown = baseInterval + Math.random() * 0.03;

  const eye = bot.eyePos();
  const toTarget = targetPos.clone().sub(eye).normalize();
  const flinchErr = bot.hitFlinch > 0 ? 0.05 : 0;
  const err = d.aimError + flinchErr;
  const dir = toTarget.clone();
  dir.x += (Math.random() - 0.5) * err;
  dir.y += (Math.random() - 0.5) * err;
  dir.z += (Math.random() - 0.5) * err;
  dir.normalize();

  Audio_.gunshot('rifle', Math.max(0.15, 1 - dist / 45));
  const muzzle = eye.clone().add(new THREE.Vector3(0.3, -0.1, 0).applyAxisAngle(new THREE.Vector3(0, 1, 0), bot.yaw));
  spawnMuzzleFlash(muzzle, bot.mesh.quaternion);

  // resolve hit vs player via cone test (player has no explicit mesh)
  const angle = dir.angleTo(toTarget);
  const hitChance = angle < 0.02 ? 1 : angle < err * 0.8 ? 0.55 : 0.08;
  if (Math.random() < hitChance && player.alive) {
    const headshot = angle < 0.006 && Math.random() < 0.5;
    let dmg = damageAtRange(WEAPONS.rifle, dist) * d.dmgMult;
    if (headshot) dmg *= WEAPONS.rifle.headshotMult;
    bot.lastShotHeadshot = headshot;
    player.takeDamage(dmg, headshot);
  }
}

function updateBot(dt) {
  if (!bot.alive) return;
  const d = bot.diffParams;
  bot.hitFlinch = Math.max(0, bot.hitFlinch - dt);

  const playerVisible = player.alive && bot.hasLineOfSight(camera.getWorldPosition(new THREE.Vector3()));
  const distToPlayer = bot.pos.distanceTo(player.pos);

  if (playerVisible) {
    bot.lastHeardPos = player.pos.clone();
    bot.lastHeardTime = GAME.clock.elapsedTime;
    if (bot.state !== 'CHASE') bot.reactionTimer = d.reaction;
    bot.state = 'CHASE';
  } else if (bot.state === 'CHASE') {
    bot.state = 'INVESTIGATE';
    bot.path = [];
  }

  // "hearing" gunfire: handled by player firing setting a heard position elsewhere (see hookPlayerGunfireHeard)

  if (bot.state === 'CHASE') {
    bot.reactionTimer -= dt;
    // face player (reaction delay before returning fire)
    const eye = bot.eyePos();
    const toPlayer = player.pos.clone().sub(eye);
    const targetYaw = Math.atan2(toPlayer.x, toPlayer.z);
    bot.yaw = lerpAngle(bot.yaw, targetYaw, Math.min(1, dt * 10));

    if (distToPlayer < 26) {
      // strafe while shooting
      bot.strafeTimer -= dt;
      if (bot.strafeTimer <= 0) { bot.strafeDir *= -1; bot.strafeTimer = 0.5 + Math.random() * 0.6; }
      const right = new THREE.Vector3(Math.cos(bot.yaw), 0, -Math.sin(bot.yaw));
      const move = right.multiplyScalar(bot.strafeDir * bot.speed * 0.6 * dt);
      moveBotBy(move.x, move.z);
      if (distToPlayer > 20) moveBotBy(Math.sin(bot.yaw) * bot.speed * 0.5 * dt, Math.cos(bot.yaw) * bot.speed * 0.5 * dt);
    } else {
      moveBotBy(Math.sin(bot.yaw) * bot.speed * dt, Math.cos(bot.yaw) * bot.speed * dt);
    }

    if (bot.reactionTimer <= 0) botFire(dt, distToPlayer, camera.getWorldPosition(new THREE.Vector3()));
  } else if (bot.state === 'INVESTIGATE') {
    const timeSinceHeard = GAME.clock.elapsedTime - bot.lastHeardTime;
    if (timeSinceHeard > 6) { bot.state = 'PATROL'; bot.path = []; }
    else {
      if (bot.path.length === 0 && bot.lastHeardPos) {
        const from = nearestNode(bot.pos);
        const to = nearestNode(bot.lastHeardPos);
        bot.path = bfsPath(from, to);
      }
      const reached = followPath(dt, bot.speed);
      if (reached) {
        // arrived near last heard pos: hold an angle sometimes, else resume patrol
        if (Math.random() < d.holdChance) { bot.state = 'HOLD'; bot.holdUntil = GAME.clock.elapsedTime + 2 + Math.random() * 2; }
        else bot.state = 'PATROL';
      }
    }
  } else if (bot.state === 'HOLD') {
    if (GAME.clock.elapsedTime > bot.holdUntil) bot.state = 'PATROL';
  } else {
    // PATROL
    if (bot.path.length === 0) {
      const from = nearestNode(bot.pos);
      const keys = Object.keys(WP);
      let to = keys[Math.floor(Math.random() * keys.length)];
      let guard = 0;
      while (to === from && guard++ < 5) to = keys[Math.floor(Math.random() * keys.length)];
      bot.path = bfsPath(from, to);
    }
    followPath(dt, bot.speed * 0.65);
  }

  // vertical + ground snap
  const gh = getGroundHeight(bot.pos.x, bot.pos.z);
  bot.pos.y = gh;
  bot.mesh.position.copy(bot.pos);
  bot.mesh.rotation.y = bot.yaw;

  // footstep sfx (distance-attenuated)
  bot.footstepTimer = (bot.footstepTimer || 0) - dt;
  const moving = bot.state !== 'HOLD';
  if (moving && bot.footstepTimer <= 0) {
    const dist = bot.pos.distanceTo(player.pos);
    if (dist < 30) Audio_.footstep(Math.max(0, 1 - dist / 30));
    bot.footstepTimer = 0.36;
  }
}

function lerpAngle(a, b, t) {
  let diff = ((b - a + Math.PI) % (Math.PI * 2)) - Math.PI;
  if (diff < -Math.PI) diff += Math.PI * 2;
  return a + diff * t;
}

function moveBotBy(dx, dz) {
  const p = bot.pos.clone();
  p.x += dx; p.z += dz;
  resolveXZCollision(p, bot.radius, p.y);
  bot.pos.x = p.x; bot.pos.z = p.z;
}

function followPath(dt, speed) {
  if (bot.path.length === 0) return true;
  const targetId = bot.path[0];
  const target = wpPos(targetId);
  const toTarget = target.clone().sub(bot.pos);
  toTarget.y = 0;
  const dist = toTarget.length();
  if (dist < 0.6) {
    bot.path.shift();
    if (bot.path.length === 0) return true;
    return false;
  }
  toTarget.normalize();
  bot.yaw = lerpAngle(bot.yaw, Math.atan2(toTarget.x, toTarget.z), Math.min(1, dt * 6));
  moveBotBy(toTarget.x * speed * dt, toTarget.z * speed * dt);
  return false;
}

// hook: when player fires, if within hearing radius bot becomes aware (even without LOS)
function hookPlayerGunfireHeard() {
  if (!bot.alive) return;
  const d = bot.diffParams;
  const dist = bot.pos.distanceTo(player.pos);
  if (dist <= d.hearing) {
    bot.lastHeardPos = player.pos.clone();
    bot.lastHeardTime = GAME.clock.elapsedTime;
    if (bot.state === 'PATROL') { bot.state = 'INVESTIGATE'; bot.path = []; }
    HUD.hearIndicator.style.opacity = 0; // (audible cue is for the player's own awareness of bot, kept subtle)
  }
}

// ---------------------------------------------------------------------
// Player movement + update
// ---------------------------------------------------------------------
const GRAVITY = 18, JUMP_SPEED = 6.6;

function updatePlayer(dt) {
  if (!player.alive) { player.fireCooldown = Math.max(0, player.fireCooldown - dt); return; }

  // mouse look
  const sensitivity = 0.0022;
  player.yaw -= mouseDX * sensitivity;
  player.pitch -= mouseDY * sensitivity;
  player.pitch = Math.max(-Math.PI / 2 + 0.05, Math.min(Math.PI / 2 - 0.05, player.pitch));
  mouseDX = 0; mouseDY = 0;

  // recoil recovery
  const w = player.currentWeapon();
  player.recoilPitch = THREE.MathUtils.lerp(player.recoilPitch, 0, Math.min(1, dt * w.recoilRecover));
  player.recoilYaw = THREE.MathUtils.lerp(player.recoilYaw, 0, Math.min(1, dt * w.recoilRecover));

  camera.rotation.order = 'YXZ';
  camera.rotation.y = player.yaw + player.recoilYaw;
  camera.rotation.x = player.pitch + player.recoilPitch;

  // movement input
  let mx = 0, mz = 0;
  if (KEYS['KeyW']) mz -= 1;
  if (KEYS['KeyS']) mz += 1;
  if (KEYS['KeyA']) mx -= 1;
  if (KEYS['KeyD']) mx += 1;
  const len = Math.hypot(mx, mz);
  if (len > 0) { mx /= len; mz /= len; }

  const walking = !!KEYS['ShiftLeft'] || !!KEYS['ShiftRight'];
  player.crouching = !!KEYS['ControlLeft'] || !!KEYS['ControlRight'];
  let speed = player.speed;
  if (walking) speed *= 0.5;
  if (player.crouching) speed *= 0.6;

  const forward = new THREE.Vector3(Math.sin(player.yaw), 0, Math.cos(player.yaw));
  const right = new THREE.Vector3(Math.cos(player.yaw), 0, -Math.sin(player.yaw));
  const move = new THREE.Vector3();
  move.addScaledVector(forward, -mz);
  move.addScaledVector(right, mx);
  if (move.lengthSq() > 0) move.normalize().multiplyScalar(speed * dt);

  player.moveSpeedNorm = len > 0 ? (walking ? 0.35 : 1.0) : 0;

  const groundH = getGroundHeight(player.pos.x, player.pos.z);
  player.grounded = player.pos.y <= groundH + 0.02 && player.vy <= 0;

  if (KEYS['Space'] && player.grounded) { player.vy = JUMP_SPEED; player.grounded = false; }

  // gravity / vertical
  player.vy -= GRAVITY * dt;
  player.pos.y += player.vy * dt;
  const newGroundH = getGroundHeight(player.pos.x, player.pos.z);
  if (player.pos.y <= newGroundH) { player.pos.y = newGroundH; player.vy = 0; player.grounded = true; }

  // horizontal movement + collision
  const prevY = player.pos.y;
  const testPos = player.pos.clone();
  testPos.x += move.x; testPos.z += move.z;
  resolveXZCollision(testPos, player.radius, prevY);
  player.pos.x = testPos.x; player.pos.z = testPos.z;

  // clamp to arena
  player.pos.x = Math.max(-ARENA_X + 0.5, Math.min(ARENA_X - 0.5, player.pos.x));
  player.pos.z = Math.max(-ARENA_Z + 0.5, Math.min(ARENA_Z - 0.5, player.pos.z));

  const eyeHeight = player.crouching ? player.crouchHeight : player.height;
  camera.position.set(player.pos.x, player.pos.y + eyeHeight, player.pos.z);

  // footsteps
  if (len > 0 && player.grounded) {
    player.footstepTimer -= dt;
    if (player.footstepTimer <= 0) {
      Audio_.footstep(walking ? 0.5 : player.crouching ? 0.35 : 0.8);
      player.footstepTimer = player.crouching ? 0.5 : walking ? 0.45 : 0.32;
    }
  } else {
    player.footstepTimer = 0;
  }

  // firing
  player.fireCooldown = Math.max(0, player.fireCooldown - dt);
  if (!mouseDown) player.firedThisClick = false;
  const beforeAmmo = player.ammo[player.currentWeapon().id];
  playerTryFire(dt);
  if (player.ammo[player.currentWeapon().id] < beforeAmmo) hookPlayerGunfireHeard();

  tryReloadFinish(dt);

  // sniper ADS (right click) simple FOV zoom
  const targetFov = rightDown && player.weaponId === 'sniper' ? DEFAULT_FOV * WEAPONS.sniper.adsFov : DEFAULT_FOV;
  camera.fov = THREE.MathUtils.lerp(camera.fov, targetFov, dt * 10);
  camera.updateProjectionMatrix();

  // crosshair spread visualization
  const spreadPx = player.crouching ? 1 : player.moveSpeedNorm > 0.05 ? 10 : 2;
  setSpread(spreadPx);
}

// ---------------------------------------------------------------------
// Start / Rematch flow
// ---------------------------------------------------------------------
HUD.diffRow.addEventListener('click', (e) => {
  const btn = e.target.closest('.diffBtn');
  if (!btn) return;
  GAME.difficulty = btn.dataset.diff;
  HUD.diffRow.querySelectorAll('.diffBtn').forEach((b) => b.classList.toggle('sel', b === btn));
});

function startMatch() {
  GAME.scoreYou = 0; GAME.scoreBot = 0;
  HUD.youScore.textContent = 'YOU 0';
  HUD.botScore.textContent = 'BOT 0';
  HUD.killfeed.innerHTML = '';
  bot.applyDifficulty(GAME.difficulty);
  const spA = pickRespawn(null);
  const spB = pickRespawn(spA);
  player.respawn(spA);
  bot.respawn(spB);
  bot.state = 'PATROL';
  GAME.running = true;
  HUD.startOverlay.classList.add('hidden');
  HUD.winOverlay.classList.add('hidden');
  canvas.requestPointerLock();
  GAME.clock.start();
}

HUD.playBtn.addEventListener('click', startMatch);
HUD.rematchBtn.addEventListener('click', startMatch);

// ---------------------------------------------------------------------
// Main loop
// ---------------------------------------------------------------------
function animate() {
  requestAnimationFrame(animate);
  const dt = Math.min(0.05, GAME.clock.getDelta());
  if (GAME.running) {
    updatePlayer(dt);
    updateBot(dt);
    sun.target.position.copy(player.pos);
  }
  renderer.render(scene, camera);
}
camera.position.set(-26, player.height, 0);
animate();
})();
import cv2
import numpy as np
import math

# Video settings
width, height = 1920, 1080
fps = 30
duration = 30  # seconds
total_frames = fps * duration
filename = 'google_spinner_animation.mp4'

# Google Colors (BGR format for OpenCV)
# Blue, Red, Yellow, Green
colors = [
    (244, 66, 66),   # Blue (#4285F4)
    (55, 68, 219),   # Red (#DB4437)
    (0, 180, 244),   # Yellow (#F4B400)
    (88, 157, 15)    # Green (#0F9D58)
]

# Initialize video writer
fourcc = cv2.VideoWriter_fourcc(*'mp4v')
video = cv2.VideoWriter(filename, fourcc, fps, (width, height))

# Animation parameters
center = (width // 2, height // 2)
radius = 100
thickness = 20

# Simulation variables
# The spinner rotates (container rotation) + expands/contracts (arc length)
rotation_speed = 3.0  # degrees per frame for container rotation
arc_speed = 4.0       # Speed of expansion/contraction
color_change_speed = 0.05 # Speed of color cycle

print(f"Generating {filename} ({duration}s)...")

for i in range(total_frames):
    # Create black background
    frame = np.zeros((height, width, 3), dtype=np.uint8)
    
    # 1. Container Rotation (Linear constant rotation)
    # 1568ms per turn in CSS ~= 1.5s. At 30fps, that's ~45 frames. 360/45 = 8 deg/frame.
    # We'll slow it down slightly for smoothness.
    container_angle = (i * 6) % 360 
    
    # 2. Arc Expansion/Contraction (Breathe effect)
    # Cycles from short arc (e.g., 10 deg) to long arc (e.g., 270 deg)
    # CSS uses cubic-bezier, we'll use a sine wave for smooth approximation
    # 5332ms cycle in CSS ~= 5.3s. 
    cycle_progress = (i % (5.3 * fps)) / (5.3 * fps)
    
    # Calculate arc length (oscillates between 30 and 300 degrees)
    # We use sin() mapped to 0-1 range, then scaled
    sine_val = math.sin(cycle_progress * 2 * math.pi)
    # Map -1..1 to 0..1
    norm_val = (sine_val + 1) / 2
    
    # Dynamic Arc Length: 
    # When growing: Head moves faster than tail.
    # When shrinking: Tail moves faster than head.
    # Simplified visual approximation:
    arc_length = 30 + (270 * norm_val)
    
    # 3. Color Selection
    # Cycle through the 4 colors
    color_index = int((i / (1.325 * fps)) % 4) # Switch color every ~1.3s
    current_color = colors[color_index]
    
    # Draw the arc
    # OpenCV ellipse takes (center, axes, angle, startAngle, endAngle, color, thickness)
    # angles are in degrees
    
    # We add an extra rotation to the start/end angles to mimic the "chasing" tail effect
    # This creates the visual effect of the arc traveling around the circle while growing/shrinking
    extra_rot = i * 4 
    
    start_angle = container_angle + extra_rot
    end_angle = start_angle + arc_length
    
    cv2.ellipse(frame, center, (radius, radius), 0, start_angle, end_angle, current_color, thickness, lineType=cv2.LINE_AA)

    video.write(frame)

video.release()
print("Video generation complete.")
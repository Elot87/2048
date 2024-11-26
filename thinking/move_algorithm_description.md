# Algorithm
---
This file really only exists to give me a place to think about how exactly this algorithm is going to work.

 1. let the direction tiles are sliding in be `d`
 2. We start by looking at the furthest column or row perpendicular to `d`
 3. We can make no actions in that row, so we move back a row
 4. We can now slide up. If any tiles overlap on attempted movement they are merged
 5. the location at which tiles were merged is marked on a boolean array
 6. when we repeat these steps for the last two rows we do not merge on tiles denotated by the boolean array to have been already merged



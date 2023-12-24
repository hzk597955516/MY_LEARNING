.globl relu

.text
# ==============================================================================
# FUNCTION: Performs an inplace element-wise ReLU on an array of ints
# Arguments:
# 	a0 (int*) is the pointer to the array
#	a1 (int)  is the # of elements in the array
# Returns:
#	None
#
# If the length of the vector is less than 1, 
# this function exits with error code 8.
# ==============================================================================
relu:

    # Prologue
    addi sp, sp, -4
    sw ra, 0(sp)
    
    addi t0, x0, 1
    blt a1, t0, exit_8
    addi t0, x0, 0

loop_start:
    bge t0, a1, loop_end
    slli t3, t0, 2
    add t1, t3, a0
    lw t2, 0(t1)
    blt t2, x0, change_arr
    addi t0, t0, 1
    j loop_start


change_arr:
    sw x0, 0(t1)
    j loop_continue

loop_continue:
    addi t0, t0, 1
    j loop_start

loop_end:
    
    # Epilogue
    lw ra, 0(sp)
    addi sp, sp, 4
	ret

exit_8:
    addi a0, x0, 17
    addi a1, x0, 8
    ecall
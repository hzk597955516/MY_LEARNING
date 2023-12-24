.globl argmax

.text
# =================================================================
# FUNCTION: Given a int vector, return the index of the largest
#	element. If there are multiple, return the one
#	with the smallest index.
# Arguments:
# 	a0 (int*) is the pointer to the start of the vector
#	a1 (int)  is the # of elements in the vector
# Returns:
#	a0 (int)  is the first index of the largest element
#
# If the length of the vector is less than 1, 
# this function exits with error code 7.
# =================================================================
argmax:

    # Prologue
    addi sp, sp, -4
    sw ra, 0(sp)
    
    addi t0, x0, 1
    blt a1, t0, exit_7
	
    addi t0, x0, 0
    lw t1, 0(a0)
    addi t2, x0, 1
loop_start:
    bge t2, a1, loop_end
    slli t3, t2, 2
    add t4, t3, a0
    lw t5, 0(t4)
    blt t1, t5, change
    j loop_continue
    
change:
    add t0, t2, x0
    add t1, t5, x0
    j loop_continue
    
loop_continue:
    addi t2, t2, 1
    j loop_start
loop_end:
    lw ra, 0(sp)
    addi sp, sp, 4
    add a0, t0, x0
    # Epilogue
    ret
exit_7:
    addi a0, x0, 17
    addi a1, x0, 7
    ecall